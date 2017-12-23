package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import gov.ca.cwds.data.legacy.cms.entity.Referral;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
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
}
