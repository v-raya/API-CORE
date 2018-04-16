package gov.ca.cwds.authorizer;

import static gov.ca.cwds.util.PerryAccountUtils.initPerryAccountWithPrivileges;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.authorizer.drools.DroolsAuthorizationService;
import gov.ca.cwds.authorizer.util.StaffPrivilegeUtil;
import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.realm.PerrySubject;
import java.util.Collections;
import org.drools.core.util.Drools;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author CWDS TPT-2 Team
 */

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("org.drools.core.util.Drools")
@PrepareForTest({PerrySubject.class, Drools.class})
public class PlacementHomeResultReadAuthorizerTest {

  static {
    System.out.println(Drools.class.getPackage());
  }

  private PlacementHomeResultReadAuthorizer testSubject;

  @Before
  public void init() {

    MockitoAnnotations.initMocks(this);
    final DroolsService droolsService = new DroolsService();
    final DroolsAuthorizationService droolsAuthorizationService = new DroolsAuthorizationService(
        droolsService);
    testSubject = new PlacementHomeResultReadAuthorizer(droolsAuthorizationService);
  }

  @Test
  public void checkInstance_returnTrue_whenNoPlacementHome() {
    assertTrue(testSubject.checkInstance(null));
  }

  @Test
  public void checkInstance_returnFalse_whenNullPrivileges() {
    final PerryAccount perryAccount = new PerryAccount();
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    PlacementHome placementHome = new PlacementHome();
    placementHome.setAdhmonly("N");
    assertFalse(testSubject.checkInstance(placementHome));
  }

  @Test
  public void checkInstance_returnFalse_whenEmptyPrivileges() {
    final PerryAccount perryAccount = new PerryAccount();
    perryAccount.setPrivileges(Collections.emptySet());
    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    PlacementHome placementHome = new PlacementHome();
    placementHome.setAdhmonly("N");
    assertFalse(testSubject.checkInstance(placementHome));
  }

  @Test
  public void checkInstance_returnTrue_whenResourceMgmtPrivilege() {
    final PerryAccount perryAccount = initPerryAccountWithPrivileges(
        StaffPrivilegeUtil.RESOURCE_MANAGEMENT);
    perryAccount.setCountyCwsCode("1126");
    perryAccount.setCountyCode("99");
    perryAccount.setCountyName("State of California");

    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    PlacementHome placementHome = new PlacementHome();
    placementHome.setAdhmonly("N");
    assertTrue(testSubject.checkInstance(placementHome));
  }

  @Test
  public void checkInstance_returnTrue_whenCWSCaseManagementSystemPrivilege() {
    final PerryAccount perryAccount = initPerryAccountWithPrivileges(
        StaffPrivilegeUtil.CWS_CASE_MANAGEMENT_SYSTEM);
    perryAccount.setCountyCwsCode("1126");
    perryAccount.setCountyCode("99");
    perryAccount.setCountyName("State of California");

    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    PlacementHome placementHome = new PlacementHome();
    placementHome.setAdhmonly("N");
    assertTrue(testSubject.checkInstance(placementHome));
  }

  @Test
  public void checkInstance_returnFalse_whenCWSCaseManagementSystemPrivilegeAndAdoptionIndicatorY() {
    final PerryAccount perryAccount = initPerryAccountWithPrivileges(
        StaffPrivilegeUtil.CWS_CASE_MANAGEMENT_SYSTEM);
    perryAccount.setCountyCwsCode("1126");
    perryAccount.setCountyCode("99");
    perryAccount.setCountyName("State of California");

    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    PlacementHome placementHome = new PlacementHome();
    placementHome.setAdhmonly("Y");
    assertFalse(testSubject.checkInstance(placementHome));
  }

  @Test
  public void checkInstance_returnTrue_whenAdoptionAndCWSCaseManagementSystemPrivilegeAndAdoptionIndicatorY() {
    final PerryAccount perryAccount = initPerryAccountWithPrivileges(
        StaffPrivilegeUtil.CWS_CASE_MANAGEMENT_SYSTEM, StaffPrivilegeUtil.ADOPTIONS);
    perryAccount.setCountyCwsCode("1126");
    perryAccount.setCountyCode("99");
    perryAccount.setCountyName("State of California");

    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    PlacementHome placementHome = new PlacementHome();
    placementHome.setAdhmonly("Y");
    assertTrue(testSubject.checkInstance(placementHome));
  }

  @Test
  public void checkInstance_returnFalse_whenAdoptionAndNoReadPrivileges() {
    final PerryAccount perryAccount = initPerryAccountWithPrivileges(StaffPrivilegeUtil.ADOPTIONS);
    perryAccount.setCountyCwsCode("1126");
    perryAccount.setCountyCode("99");
    perryAccount.setCountyName("State of California");

    mockStatic(PerrySubject.class);
    when(PerrySubject.getPerryAccount()).thenReturn(perryAccount);

    PlacementHome placementHome = new PlacementHome();
    placementHome.setAdhmonly("Y");
    assertFalse(testSubject.checkInstance(placementHome));
    placementHome.setAdhmonly("N");
    assertFalse(testSubject.checkInstance(placementHome));

  }
}