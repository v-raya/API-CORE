package gov.ca.cwds.cms.data.access.service.impl.dbDependent;

import gov.ca.cwds.cms.data.access.service.impl.SafetyAlertService;
import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.dao.SafetyAlertDao;
import gov.ca.cwds.data.legacy.cms.entity.LongText;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SafetyAlertActivationReasonType;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

public class SafetyAlertServiceTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final LocalDate NEW_ACTIVATION_DATE = LocalDate.of(2017, 12, 12);
  private static final LocalDate NEW_DEACTIVATION_DATE = LocalDate.of(2018, 2, 10);
  private static final String CLIENT_ID = "R06FKZ20X5";
  private static final String USER_ID = "0X7";
  private static final short COUNTY_SYSTEM_ID = 1126;

  private SafetyAlertService safetyAlertService;

  @Before
  public void init() {
    SafetyAlertDao safetyAlertDao = new SafetyAlertDao(sessionFactory);
    safetyAlertService = new SafetyAlertService(safetyAlertDao);
    initUserAccount(USER_ID);
  }

  @Test
  public void updateTest() throws Exception {
    cleanAllAndInsert("/dbunit/SafetyAlerts.xml");

    List<SafetyAlert> forUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          forUpdate.addAll(safetyAlertService.findSafetyAlertsByClientId(CLIENT_ID));
        });

    assertEquals(forUpdate.size(), 2);
    SafetyAlert first = forUpdate.get(0);
    first.setActivationDate(NEW_ACTIVATION_DATE); // update
    first.setDeactivationDate(NEW_DEACTIVATION_DATE); // update
    String updatedThirdId = first.getThirdId();
    forUpdate.remove(1); // delete

    SafetyAlert newSafetyAlert = getSafetyAlert();

    forUpdate.add(newSafetyAlert); // new

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          safetyAlertService.updateSafetyAlertsByClientId(CLIENT_ID, forUpdate);
        });

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Collection<SafetyAlert> afterUpdate =
              safetyAlertService.findSafetyAlertsByClientId(CLIENT_ID);
          assertEquals(afterUpdate.size(), 2);
          for (SafetyAlert testOne : afterUpdate) {
            if (testOne.getThirdId().equals(updatedThirdId)) {
              assertEquals(NEW_ACTIVATION_DATE, testOne.getActivationDate());
              assertEquals(NEW_DEACTIVATION_DATE, testOne.getDeactivationDate());
              assertEquals(USER_ID, testOne.getLastUpdateId());
            } else {
              assertTrue(testOne.getActivationGovernmentEntityType().getSystemId() == COUNTY_SYSTEM_ID);
              assertEquals(USER_ID, testOne.getLastUpdateId());
            }
          }
        });
  }

  @Test
  public void shouldFindByClientId() throws Exception {
    cleanAllAndInsert("/dbunit/SafetyAlerts.xml");
    List<SafetyAlert> safetyAlerts = new ArrayList<>();
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          safetyAlerts.addAll(safetyAlertService.findSafetyAlertsByClientId(CLIENT_ID));
        });
    assertEquals(safetyAlerts.size(), 2);
  }

  private SafetyAlert getSafetyAlert() {
    SafetyAlert newSafetyAlert = new SafetyAlert();
    newSafetyAlert.setActivationDate(LocalDate.now());
    newSafetyAlert.setFkClientId(CLIENT_ID);
    SafetyAlertActivationReasonType safetyAlertActivationReasonType =
        new SafetyAlertActivationReasonType();
    safetyAlertActivationReasonType.setSystemId((short) 6403);
    LongText longText = new LongText();
    longText.setIdentifier("8Dz06wW0X5");
    newSafetyAlert.setActivationReasonType(safetyAlertActivationReasonType);
    newSafetyAlert.setActivationExplanationText(longText);
    County county = new County();
    county.setSystemId(COUNTY_SYSTEM_ID);
    newSafetyAlert.setActivationGovernmentEntityType(county);
    return newSafetyAlert;
  }

  private void initUserAccount(String userAccount) {
    PerryAccount perryAccount = new PerryAccount();
    mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(userAccount);
  }
}
