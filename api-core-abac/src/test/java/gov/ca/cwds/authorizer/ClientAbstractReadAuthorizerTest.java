package gov.ca.cwds.authorizer;

import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.CWS_CASE_MANAGEMENT_SYSTEM;
import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.SEALED;
import static gov.ca.cwds.authorizer.util.StaffPrivilegeUtil.SENSITIVE_PERSONS;
import static gov.ca.cwds.util.PerryAccountUtils.initPerryAccountWithPrivileges;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.ClientAbstractAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import gov.ca.cwds.service.ClientCountyDeterminationService;
import gov.ca.cwds.service.ClientSensitivityDeterminationService;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author CWDS TPT-3 Team
 */
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("org.drools.core.util.Drools")
@PrepareForTest(PerrySubject.class)
public class ClientAbstractReadAuthorizerTest {

  private static final String CLIENT_ID = "id";

  @Mock
  private ClientSensitivityDeterminationService clientSensitivityDeterminationService;

  @Mock
  private ClientCountyDeterminationService clientCountyDeterminationService;

  private ClientAbstractReadAuthorizer testSubject;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    final DroolsService droolsService = new DroolsService();
    final DroolsAuthorizationService droolsAuthorizationService = new DroolsAuthorizationService(droolsService);
    final ClientAbstractAuthorizationDroolsConfiguration droolsConfiguration = new ClientAbstractAuthorizationDroolsConfiguration();
    testSubject = new ClientAbstractReadAuthorizer(droolsAuthorizationService, droolsConfiguration);
    testSubject.setSensitivityDeterminationService(clientSensitivityDeterminationService);
    testSubject.setCountyDeterminationService(clientCountyDeterminationService);
  }

  @Test
  public void checkNullInstance() {
    // Social Worker Only
    initUserAccount("1126","State of California", CWS_CASE_MANAGEMENT_SYSTEM);
    assertTrue(testSubject.checkInstance(null));
  }

  @Test
  public void checkNullSensitivity() {
    when(clientSensitivityDeterminationService.getClientSensitivityById(anyString())).thenReturn(null);
    assertTrue(testSubject.checkId("1"));
  }

  @Test
  public void clientNoConditionsTest() {
    when(clientSensitivityDeterminationService.getClientSensitivityById(CLIENT_ID))
        .thenReturn(Sensitivity.NOT_APPLICABLE);
    when(clientCountyDeterminationService.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1023, 1034}));
    final Client client = initClient(Sensitivity.NOT_APPLICABLE);
    checkAllCases(client, "1073", "Colusa", true, true, true, true, true);
  }

  @Test
  public void clientSameCountySensitiveTest() {
    when(clientSensitivityDeterminationService.getClientSensitivityById(CLIENT_ID))
        .thenReturn(Sensitivity.SENSITIVE);
    when(clientCountyDeterminationService.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1073, 1126}));
    final Client client = initClient(Sensitivity.SENSITIVE);
    checkAllCases(client, "1073", "Colusa", false, true, false, true, false);
  }

  @Test
  public void clientSameCountySealedTest() {
    when(clientSensitivityDeterminationService.getClientSensitivityById(CLIENT_ID))
        .thenReturn(Sensitivity.SEALED);
    when(clientCountyDeterminationService.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1073, 1126}));
    final Client client = initClient(Sensitivity.SEALED);
    checkAllCases(client, "1073", "Colusa", false, false, true, false, true);
  }

  @Test
  public void clientDifferentCountySensitiveTest() {
    when(clientSensitivityDeterminationService.getClientSensitivityById(CLIENT_ID))
        .thenReturn(Sensitivity.SENSITIVE);
    when(clientCountyDeterminationService.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1034, 1012}));
    final Client client = initClient(Sensitivity.SENSITIVE);
    checkAllCases(client, "1073", "Colusa", false, false, false, false, false);
  }

  @Test
  public void clientDifferentCountySealedTest() {
    when(clientSensitivityDeterminationService.getClientSensitivityById(CLIENT_ID))
        .thenReturn(Sensitivity.SEALED);
    when(clientCountyDeterminationService.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{1034, 1012}));
    final Client client = initClient(Sensitivity.SEALED);
    checkAllCases(client, "1073", "Colusa", false, false, false, false, false);
  }

  @Test
  public void clientNoCountySensitiveTest() {
    when(clientSensitivityDeterminationService.getClientSensitivityById(CLIENT_ID))
        .thenReturn(Sensitivity.SENSITIVE);
    when(clientCountyDeterminationService.getClientCountiesById(anyString()))
        .thenReturn(null);
    final Client client = initClient(Sensitivity.SENSITIVE);
    checkAllCases(client, "1073", "Colusa", false, true, false, false, false);
  }

  @Test
  public void clientNoCountySealedTest() {
    when(clientSensitivityDeterminationService.getClientSensitivityById(CLIENT_ID))
        .thenReturn(Sensitivity.SEALED);
    when(clientCountyDeterminationService.getClientCountiesById(anyString()))
        .thenReturn(null);
    final Client client = initClient(Sensitivity.SEALED);
    checkAllCases(client, "1073", "Colusa", false, false, true, false, true);
  }

  private void checkAllCases(Client client, String userCountyCwsCode, String userCountyName, boolean socialWorkerOnly, boolean countySensitive, boolean countySealed, boolean stateSensitive, boolean stateSealed) {
    // Social Worker Only
    initUserAccount(userCountyCwsCode, userCountyName, CWS_CASE_MANAGEMENT_SYSTEM);
    validateInstance(client, socialWorkerOnly);

    // County Sensitive
    initUserAccount(userCountyCwsCode, userCountyName, SENSITIVE_PERSONS);
    validateInstance(client, countySensitive);

    // County Sealed
    initUserAccount(userCountyCwsCode, userCountyName, SEALED);
    validateInstance(client, countySealed);

    // State Sensitive
    initUserAccount("1126","State of California", SENSITIVE_PERSONS);
    validateInstance(client, stateSensitive);

    // State Sealed
    initUserAccount("1126", "State of California", SEALED);
    validateInstance(client, stateSealed);
  }

  private void initUserAccount(String userCountyCwsCode, String userCountyName, String... privileges) {
    final PerryAccount perryAccount = initPerryAccountWithPrivileges(privileges);
    perryAccount.setCountyCwsCode(userCountyCwsCode);
    perryAccount.setCountyName(userCountyName);
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
  }

  private void validateInstance(Client client, boolean result) {
    assertEquals(result, testSubject.checkId(client.getIdentifier()));
    assertEquals(result, testSubject.checkInstance(client));
    PerrySubject.getPerryAccount();
  }

  private Client initClient(Sensitivity sensitivity) {
    final Client client = new Client();
    client.setIdentifier(CLIENT_ID);
    client.setSensitivity(sensitivity);
    return client;
  }
}