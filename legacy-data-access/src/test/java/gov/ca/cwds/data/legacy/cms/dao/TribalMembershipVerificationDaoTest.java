package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.TribalMembershipVerification;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class TribalMembershipVerificationDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String CHILD_CLIENT_ID = "RM1Mq5GABD";
  public static final String PARENT_CLIENT_ID = "RM1Mq5GABC";

  private TribalMembershipVerificationDao dao = null;

  @Before
  public void before() {
    dao = new TribalMembershipVerificationDao(sessionFactory);
  }

  @Test
  public void findSubTribalsByClientId() throws Exception {
    cleanAllAndInsert("/dbunit/TribalMembershipVerification.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory -> {
          List<TribalMembershipVerification> subTribals =
              dao.findTribalsThatHaveSubTribalsByClientId(CHILD_CLIENT_ID, PARENT_CLIENT_ID);
          assertNotNull(subTribals);
          assertEquals(1,subTribals.size() );
          assertEquals("AdrY41t0Ku", subTribals.get(0).getThirdId());
          assertEquals(CHILD_CLIENT_ID, subTribals.get(0).getClientId());
        });
  }

  @Test
  public void findByClientIdNoTribalEligFrom() throws Exception {

  }

  @Test
  public void findByClientId() throws Exception {
    cleanAllAndInsert("/dbunit/TribalMembershipVerification.xml");

    executeInTransaction(
      sessionFactory,
      sessionFactory -> {
        List<TribalMembershipVerification> tribals =
          dao.findByClientId(CHILD_CLIENT_ID);
        assertNotNull(tribals);
        assertEquals(1,tribals.size() );
        assertEquals("AdrY41t0Ku", tribals.get(0).getThirdId());
        assertEquals(CHILD_CLIENT_ID, tribals.get(0).getClientId());
      });
  }

  @Test
  public void findByClientIds() throws Exception {}

  @Test
  public void findTribalsThatHaveSubTribalsByClientId() throws Exception {}
}
