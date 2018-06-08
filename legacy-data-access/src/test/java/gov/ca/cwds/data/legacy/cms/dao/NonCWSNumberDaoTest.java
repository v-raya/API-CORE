package gov.ca.cwds.data.legacy.cms.dao;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.NonCWSNumber;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;

/**
 * Unit tests for NonCWSNumberDao.
 * 
 * @author CWS TPT Team
 */
public class NonCWSNumberDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private NonCWSNumberDao dao = null;

  @Before
  public void before() throws Exception {
    dao = new NonCWSNumberDao(sessionFactory);
  }

  @Test
  public void testCreate() throws Exception {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      NonCWSNumber expected = createNonCWSNumber();
      NonCWSNumber actual = dao.create(expected);
      Assert.assertEquals(expected, actual);
    });
  }

  private static NonCWSNumber createNonCWSNumber() {
    NonCWSNumber nonCwsId = new NonCWSNumber();

    nonCwsId.setClientId("AaiU7IW0Rt");
    nonCwsId.setOtherId("braceletid");
    nonCwsId.setThirdId("BaiU7IW0Rt");
    nonCwsId.setOtherIdCode((short) 1331);
    nonCwsId.setLastUpdateTime(LocalDateTime.of(2017, 11, 01, 12, 53, 07, 580225));
    nonCwsId.setLastUpdateId("0X5");
    return nonCwsId;
  }
}
