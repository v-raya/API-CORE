package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.CWS_CASE_MANAGEMENT_SYSTEM;
import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.SEALED;
import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.SENSITIVE_PERSONS;
import static gov.ca.cwds.util.PerryAccountUtils.initPerryAccountWithPrivileges;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.ClientResultAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import gov.ca.cwds.service.ClientCountyDeterminationService;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author CWDS TPT-2 Team
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PerrySubject.class)
public class ClientResultReadAuthorizerTest {

  private static final String CLIENT_ID = "id";

  @Mock
  private ClientCountyDeterminationService clientCountyDeterminationServiceMock;

  private ClientResultReadAuthorizer testSubject;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    final DroolsService droolsService = new DroolsService();
    final DroolsAuthorizationService droolsAuthorizationService = new DroolsAuthorizationService(droolsService);
    testSubject = new ClientResultReadAuthorizer(droolsAuthorizationService);
    testSubject.setDroolsAuthorizationService(droolsAuthorizationService);
    testSubject.setDroolsConfiguration(new ClientResultAuthorizationDroolsConfiguration());
    testSubject.setCountyDeterminationService(clientCountyDeterminationServiceMock);
  }

  @Test
  public void checkInstance_returnFalse_whenPerryPrivilegesAreNull() {
    final PerryAccount perryAccount = new PerryAccount();
    final boolean expectedResult = false;
    checkInstance_returnExpected_withPreparedPerryAccount(perryAccount, expectedResult);
  }

  @Test
  public void checkInstance_returnFalse_whenPerryPrivilegesAreEmpty() {
    final PerryAccount perryAccount = initPerryAccountWithPrivileges();
    final boolean expectedResult = false;
    checkInstance_returnExpected_withPreparedPerryAccount(perryAccount, expectedResult);
  }

  private void checkInstance_returnExpected_withPreparedPerryAccount(PerryAccount perryAccount,
      boolean expectedResult) {
    // given
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    // when
    final boolean actual = testSubject.checkInstance(null);

    // then
    assertThat(actual, is(expectedResult));
    verifyStatic(PerrySubject.class, times(0));
    PerrySubject.getPerryAccount();
  }

  @Test
  public void clientNoConditionsTest() {
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1023, 1034}));
    final Client client = initClient(Sensitivity.NOT_APPLICABLE);
    checkAllCases(client, "1073", "Colusa", true, true, true, true, true);
  }

  @Test
  public void clientSameCountySensitiveTest() {
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1073, 10}));
    final Client client = initClient(Sensitivity.SENSITIVE);
    checkAllCases(client, "1073", "Colusa", true, true, true, true, true);
  }

  @Test
  public void clientSameCountySealedTest() {
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1073, 1126}));
    final Client client = initClient(Sensitivity.SEALED);
    checkAllCases(client, "1073", "Colusa", false, false, true, false, true);
  }

  @Test
  public void clientDifferentCountySensitiveTest() {
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1034, 1012}));
    final Client client = initClient(Sensitivity.SENSITIVE);
    checkAllCases(client, "1073", "Colusa", true, true, true, true, true);
  }

  @Test
  public void clientDifferentCountySealedTest() {
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1034, 1012}));
    final Client client = initClient(Sensitivity.SEALED);
    checkAllCases(client, "1073", "Colusa", false, false, false, false, false);
  }

  @Test
  public void clientNoCountySensitiveTest() {
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(null);
    final Client client = initClient(Sensitivity.SENSITIVE);
    checkAllCases(client, "1073", "Colusa", true, true, true, true, true);
  }

  @Test
  public void clientNoCountySealedTest() {
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(null);
    final Client client = initClient(Sensitivity.SEALED);
    checkAllCases(client, "1073", "Colusa", false, false, true, false, true);
  }

  private void checkAllCases(Client client, String userCountyCwsCode, String userCountyName, boolean socialWorkerOnly, boolean countySensitive, boolean countySealed, boolean stateSensitive, boolean stateSealed) {
    // Social Worker Only
    initUserAccount(userCountyCwsCode, userCountyName, CWS_CASE_MANAGEMENT_SYSTEM);
    validateAuthorizationResult(client, socialWorkerOnly);

    // County Sensitive
    initUserAccount(userCountyCwsCode, userCountyName, SENSITIVE_PERSONS);
    validateAuthorizationResult(client, countySensitive);

    // County Sealed
    initUserAccount(userCountyCwsCode, userCountyName, SEALED);
    validateAuthorizationResult(client, countySealed);

    // State Sensitive
    initUserAccount("1126","State of California", SENSITIVE_PERSONS);
    validateAuthorizationResult(client, stateSensitive);

    // State Sealed
    initUserAccount("1126", "State of California", SEALED);
    validateAuthorizationResult(client, stateSealed);
  }

  private void initUserAccount(String userCountyCwsCode, String userCountyName, String... privileges) {
    final PerryAccount perryAccount = initPerryAccountWithPrivileges(privileges);
    perryAccount.setCountyCwsCode(userCountyCwsCode);
    perryAccount.setCountyName(userCountyName);
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
  }

  private void validateAuthorizationResult(Client client, boolean result) {
    final boolean actual = testSubject.checkInstance(client);
    assertThat(actual, is(result));
    PerrySubject.getPerryAccount();
  }

  private Client initClient(Sensitivity sensitivity) {
    final Client client = new Client();
    client.setIdentifier(CLIENT_ID);
    client.setSensitivity(sensitivity);
    return client;
  }
}