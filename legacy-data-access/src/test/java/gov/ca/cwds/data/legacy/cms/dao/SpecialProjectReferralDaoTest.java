package gov.ca.cwds.data.legacy.cms.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;

public class SpecialProjectReferralDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private SpecialProjectReferralDao dao = null;

  @Before
  public void before() throws Exception {
    dao = new SpecialProjectReferralDao(sessionFactory);
  }

  @Test
  public void testCreate() throws Exception {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      SpecialProjectReferral expected = new SpecialProjectReferral();
      expected.setId("AaiU7IW0Rt");
      expected.setReferralId("BaiU7IW0Rt");
      expected.setSpecialProjectId("CaiU7IW0Rt");
      expected.setCountySpecificCode("99");
      expected.setSsbIndicator(Boolean.TRUE);
      expected.setPartStartDate(LocalDate.of(2010, 01, 01));
      expected.setPartEndDate(LocalDate.of(2018, 01, 01));
      expected.setLastUpdateTime(LocalDateTime.now());
      expected.setLastUpdateId("0X5");

      SpecialProjectReferral actual = dao.create(expected);

      Assert.assertEquals(expected, actual);
    });
  }
  
  @Test
  public void shouldFindSpecialProjectReferralsByReferralIdAndSpecialProject() throws Exception {
    cleanAllAndInsert("/dbunit/SpecialProjectReferral.xml");
    
    executeInTransaction(sessionFactory, (sessionFactor) -> {
      List<SpecialProjectReferral> specialProjectReferrals = 
          dao.findSpecialProjectReferralsByReferralIdAndSpecialProjectId("0gSp7ME0OD", "HC9AsEcaab");
      Assert.assertEquals(1,  specialProjectReferrals.size());
      SpecialProjectReferral specialProjectReferral = specialProjectReferrals.get(0);
      Assert.assertEquals("19", specialProjectReferral.getCountySpecificCode());
      Assert.assertEquals("0gSp7ME0OD", specialProjectReferral.getReferralId());
      Assert.assertEquals("Ee3k1o60OD", specialProjectReferral.getId());
    });
  }

  @Test
  public void shouldNotFindSpecialProjectReferralsByReferralIdAndSpecialProject() throws Exception {
    cleanAllAndInsert("/dbunit/SpecialProjectReferral.xml");
    
    executeInTransaction(sessionFactory, (sessionFactor) -> {
      List<SpecialProjectReferral> specialProjectReferrals = 
          dao.findSpecialProjectReferralsByReferralIdAndSpecialProjectId("1234567ABC", "1234567ZZZ");
      Assert.assertEquals(0,  specialProjectReferrals.size());
    });
  }

}
