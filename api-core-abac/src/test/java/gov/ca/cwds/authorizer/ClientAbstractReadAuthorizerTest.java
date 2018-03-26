package gov.ca.cwds.authorizer;

import static gov.ca.cwds.util.PerryAccountUtils.initPerryAccountWithPrivileges;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.drools.configuration.ClientAuthorizationDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
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
 * @author CWDS TPT-3 Team
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PerrySubject.class)
public class ClientAbstractReadAuthorizerTest {

  private static final String CLIENT_ID = "id";

  @Mock
  private ClientCountyDeterminationService clientCountyDeterminationServiceMock;

  @Mock
  private ClientDao clientDaoMock;

  private ClientAbstractReadAuthorizer testSubject;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    final DroolsService droolsService = new DroolsService();
    final DroolsAuthorizationService droolsAuthorizationService = new DroolsAuthorizationService(droolsService);
    final ClientAuthorizationDroolsConfiguration droolsConfiguration = new ClientAuthorizationDroolsConfiguration();
    testSubject = new ClientAbstractReadAuthorizer(droolsAuthorizationService, droolsConfiguration);
    testSubject.setClientDao(clientDaoMock);
    testSubject.setDroolsAuthorizationService(droolsAuthorizationService);
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
  public void checkInstance_true_fullHappyPath() {
    // given
    final PerryAccount perryAccount = initPerryAccountWithPrivileges("Sensitive Persons");
    perryAccount.setCountyCwsCode("100");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{100, 10}));
    final Client client = initClient(Sensitivity.SENSITIVE);

    // when
    final boolean actual = testSubject.checkInstance(client);

    // then
    assertThat(actual, is(true));
    verifyStatic(PerrySubject.class, times(2));
    PerrySubject.getPerryAccount();
    verify(clientCountyDeterminationServiceMock, times(1)).getClientCountiesById(anyString());
  }

  @Test
  public void checkInstance_false_fullHappyPath() {
    // given
    final PerryAccount perryAccount = initPerryAccountWithPrivileges("Sensitive Persons");
    perryAccount.setCountyCwsCode("1");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{100, 10}));
    final Client client = initClient(Sensitivity.SENSITIVE);

    // when
    final boolean actual = testSubject.checkInstance(client);

    // then
    assertThat(actual, is(false));
    verifyStatic(PerrySubject.class, times(2));
    PerrySubject.getPerryAccount();
    verify(clientCountyDeterminationServiceMock, times(1)).getClientCountiesById(anyString());
  }

  @Test
  public void checkId_true_fullHappyPath() {
    // given
    final PerryAccount perryAccount = initPerryAccountWithPrivileges("Sensitive Persons");
    perryAccount.setCountyCwsCode("100");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
    when(clientDaoMock.find(anyString())).thenReturn(initClient(Sensitivity.SENSITIVE));
    when(clientCountyDeterminationServiceMock.getClientCountiesById(anyString()))
        .thenReturn(Arrays.asList(new Short[]{100, 10}));

    // when
    final boolean actual = testSubject.checkId(CLIENT_ID);

    // then
    assertThat(actual, is(true));
    verifyStatic(PerrySubject.class, times(2));
    PerrySubject.getPerryAccount();
    verify(clientDaoMock, times(1)).find(anyString());
    verify(clientCountyDeterminationServiceMock, times(1)).getClientCountiesById(anyString());
  }

  @Test
  public void checkId_true_whenNoClientExists() {
    // given
    when(clientDaoMock.find(CLIENT_ID)).thenReturn(null);

    // when
    final boolean actual = testSubject.checkId(CLIENT_ID);

    // then
    assertThat(actual, is(true));
    verify(clientDaoMock, times(1)).find(anyString());
  }

  @Test
  public void testCheckIdAuthorized() throws Exception {
    ClientAbstractReadAuthorizer spy = spy(testSubject);

    Client client = initClient(null);
    when(clientDaoMock.find(anyString())).thenReturn(client);
    doReturn(true).when(spy).checkInstance(any());

    spy.setClientDao(clientDaoMock);

    assertTrue(spy.checkId("-1"));
  }

  @Test
  public void testCheckIdUnauthorized() throws Exception {
    ClientAbstractReadAuthorizer spy = spy(testSubject);

    Client client = initClient(null);
    when(clientDaoMock.find(anyString())).thenReturn(client);
    doReturn(false).when(spy).checkInstance(any());

    spy.setClientDao(clientDaoMock);

    assertFalse(spy.checkId("-1"));
  }

  private Client initClient(Sensitivity sensitivity) {
    final Client client = new Client();
    client.setIdentifier(CLIENT_ID);
    client.setSensitivity(sensitivity);
    return client;
  }
}