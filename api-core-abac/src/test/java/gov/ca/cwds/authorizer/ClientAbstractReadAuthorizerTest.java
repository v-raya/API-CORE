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
import gov.ca.cwds.cms.data.access.service.ClientCoreService;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import gov.ca.cwds.service.ClientCountyDeterminationService;
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

  @Mock
  private ClientCountyDeterminationService clientCountyDeterminationServiceMock;

  @Mock
  private ClientCoreService clientCoreServiceMock;

  private ClientAbstractReadAuthorizer testSubject;

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    final DroolsService droolsService = new DroolsService();
    final DroolsAuthorizationService droolsAuthorizationService = new DroolsAuthorizationService(droolsService);
    testSubject = new ClientAbstractReadAuthorizer();
    testSubject.setClientCoreService(clientCoreServiceMock);
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
    verifyStatic(PerrySubject.class, times(1));
    PerrySubject.getPerryAccount();
  }

  @Test
  public void checkInstance_true_fullHappyPath() {
    // given
    final PerryAccount perryAccount = initPerryAccountWithPrivileges("Sensitive Persons");
    perryAccount.setGovernmentEntityType("100");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
    when(clientCountyDeterminationServiceMock.getClientCountyById(anyString()))
        .thenReturn((short) 100);
    final Client client = initClient(Sensitivity.SENSITIVE);

    // when
    final boolean actual = testSubject.checkInstance(client);

    // then
    assertThat(actual, is(true));
    verifyStatic(PerrySubject.class, times(1));
    PerrySubject.getPerryAccount();
    verify(clientCountyDeterminationServiceMock, times(1)).getClientCountyById(anyString());
  }

  @Test
  public void checkInstance_false_fullHappyPath() {
    // given
    final PerryAccount perryAccount = initPerryAccountWithPrivileges("Sensitive Persons");
    perryAccount.setGovernmentEntityType("1");
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);
    when(clientCountyDeterminationServiceMock.getClientCountyById(anyString()))
        .thenReturn((short) 100);
    final Client client = initClient(Sensitivity.SENSITIVE);

    // when
    final boolean actual = testSubject.checkInstance(client);

    // then
    assertThat(actual, is(false));
    verifyStatic(PerrySubject.class, times(1));
    PerrySubject.getPerryAccount();
    verify(clientCountyDeterminationServiceMock, times(1)).getClientCountyById(anyString());
  }

  @Test
  public void testCheckIdAuthorized() throws Exception {
    ClientAbstractReadAuthorizer spy = spy(testSubject);

    Client client = initClient(null);
    when(clientCoreServiceMock.find(anyString())).thenReturn(client);
    doReturn(true).when(spy).checkInstance(any());

    spy.setClientCoreService(clientCoreServiceMock);

    assertTrue(spy.checkId("-1"));
  }

  @Test
  public void testCheckIdUnauthorized() throws Exception {
    ClientAbstractReadAuthorizer spy = spy(testSubject);

    Client client = initClient(null);
    when(clientCoreServiceMock.find(anyString())).thenReturn(client);
    doReturn(false).when(spy).checkInstance(any());

    spy.setClientCoreService(clientCoreServiceMock);

    assertFalse(spy.checkId("-1"));
  }

  private Client initClient(Sensitivity sensitivity) {
    final Client client = new Client();
    client.setIdentifier("id");
    client.setSensitivity(sensitivity);
    return client;
  }
}