package gov.ca.cwds.cms.data.access.service.impl.dbDependent;

import gov.ca.cwds.cms.data.access.service.impl.CsecHistoryService;
import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.dao.CsecHistoryDao;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SexualExploitationType;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/** @author CWDS TPT-3 Team */
public class CsecHistoryServiceTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final LocalDate NEW_START_DATE = LocalDate.of(2017, 12, 12);
  private static final LocalDate NEW_END_DATE = LocalDate.of(2018, 2, 10);
  private static final String CLIENT_ID = "R06FKZ20X5";
  private static final String USER_ID = "0X7";
  private static final short SET_SYS_ID = 6872;

  private CsecHistoryService csecHistoryService;

  @Before
  public void init() {
    initUserAccount(USER_ID);
    CsecHistoryDao csecHistoryDao = new CsecHistoryDao(sessionFactory);
    csecHistoryService = new CsecHistoryService(csecHistoryDao);
  }

  @Test
  public void findByClientId() throws Exception {
    cleanAllAndInsert("/dbunit/CsecHistories.xml");
    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          List<CsecHistory> csecHistories = csecHistoryService.findByClientId(CLIENT_ID);
          assertNotNull(csecHistories);
          assertEquals(2, csecHistories.size());
          assertEquals(CLIENT_ID, csecHistories.get(0).getChildClient());
        });
  }

  @Test
  public void updateTest() throws Exception {
    cleanAllAndInsert("/dbunit/CsecHistories.xml");

    List<CsecHistory> forUpdate = new ArrayList<>();

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> forUpdate.addAll(csecHistoryService.findByClientId(CLIENT_ID)));

    assertEquals(forUpdate.size(), 2);
    CsecHistory first = forUpdate.get(0);
    first.setStartDate(NEW_START_DATE); // update
    first.setEndDate(NEW_END_DATE); // update
    String updatedThirdId = first.getThirdId();
    forUpdate.remove(1); // delete

    CsecHistory newCsecHistory = getCsecHistory();
    forUpdate.add(newCsecHistory); // new

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          csecHistoryService.updateCsecHistoriesByClientId(CLIENT_ID, forUpdate);
        });

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Collection<CsecHistory> afterUpdate = csecHistoryService.findByClientId(CLIENT_ID);
          assertEquals(afterUpdate.size(), 2);
          for (CsecHistory testOne : afterUpdate) {
            if (testOne.getThirdId().equals(updatedThirdId)) {
              assertEquals(NEW_START_DATE, testOne.getStartDate());
              assertEquals(NEW_END_DATE, testOne.getEndDate());
              assertEquals(USER_ID, testOne.getLastUpdateId());
            } else {
              assertTrue(testOne.getSexualExploitationType().getSystemId() == SET_SYS_ID);
              assertEquals(USER_ID, testOne.getLastUpdateId());
            }
          }
        });
  }

  private CsecHistory getCsecHistory() {
    CsecHistory csecHistory = new CsecHistory();
    csecHistory.setStartDate(LocalDate.of(2012, 12, 12));
    csecHistory.setCreationTimestamp(LocalDateTime.now());
    csecHistory.setChildClient(CLIENT_ID);
    SexualExploitationType sexualExploitationType = new SexualExploitationType();
    sexualExploitationType.setSystemId(SET_SYS_ID);
    csecHistory.setSexualExploitationType(sexualExploitationType);
    return csecHistory;
  }

  private void initUserAccount(String userAccount) {
    PerryAccount perryAccount = new PerryAccount();
    mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(userAccount);
  }
}
