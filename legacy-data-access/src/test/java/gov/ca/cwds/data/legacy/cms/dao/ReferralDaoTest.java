package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.ca.cwds.data.legacy.cms.entity.Referral;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.util.NullOrEmptyException;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class ReferralDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private ReferralDao dao = null;
  public static final String CLIENT_ID = "00xVOYD00c";

  @Before
  public void before() throws Exception {
    dao = new ReferralDao(sessionFactory);
  }

  @Test
  public void testGetActiveReferralsByClient() throws Exception {
    cleanAllAndInsert("/dbunit/Referrals.xml");
    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<Referral> referrals = dao.getActiveReferralsByClientId(CLIENT_ID);
      assertNotNull(referrals);
      assertEquals(1, referrals.size());
    });
  }

  @Test
  public void testGetClosedReferralsByClient() throws Exception {
    cleanAllAndInsert("/dbunit/Referrals.xml");
    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<Referral> referrals = dao.getClosedReferralsByClientId(CLIENT_ID);
      assertNotNull(referrals);
      assertEquals(1, referrals.size());
    });
  }

  @Test
  public void testGetReferralsByClient() throws Exception {
    cleanAllAndInsert("/dbunit/Referrals.xml");
    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<Referral> referrals = dao.getReferralsByClientId(CLIENT_ID);
      assertNotNull(referrals);
      assertEquals(2, referrals.size());
    });
  }

  @Test(expected = NullOrEmptyException.class)
  public void findReferralIdsByClientIdAndActiveDate_exception_whenNoClientIdProvided() throws Exception {
    dao.findReferralIdsByClientIdAndActiveDate(null, null);
  }

  @Test
  public void findReferralIdsByClientIdAndActiveDate_emptyList_whenNothingFound() throws Exception {
    cleanAllAndInsert("/dbunit/ClientCountByStaff.xml");
    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<String> actual = dao.findReferralIdsByClientIdAndActiveDate(CLIENT_ID, null);
      assertEquals(0, actual.size());
    });
  }

  @Test
  public void findReferralIdsByClientIdAndActiveDate_success_whenFourRecordsFound() throws Exception {
    cleanAllAndInsert("/dbunit/ClientCountByStaff.xml");
    executeInTransaction(sessionFactory, sessionFactory1 -> {
      List<String> actual = dao.findReferralIdsByClientIdAndActiveDate("ELUSKDE0Ht", LocalDate.of(1999, 1, 1));
      assertEquals(3, actual.size());
      assertEquals("BSd9H310Ht", actual.get(0));
      assertEquals("4rTFovN0Ht", actual.get(1));
      assertEquals("Lo2fZjQ0Ht", actual.get(2));
    });
  }
}
