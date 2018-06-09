package gov.ca.cwds.data.legacy.cms.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.SafelySurrenderedBabies;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;

public class SafelySurrenderedBabiesDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private SafelySurrenderedBabiesDao dao = null;

  @Before
  public void before() throws Exception {
    dao = new SafelySurrenderedBabiesDao(sessionFactory);
  }

  @Test
  public void testCreate() throws Exception {
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      SafelySurrenderedBabies expected = createSafelySurrenderedBabies();
      SafelySurrenderedBabies actual = dao.create(expected);
      Assert.assertEquals(expected, actual);
    });
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/SafelySurrenderedBabies.xml");

    executeInTransaction(sessionFactory, sessionFactory1 -> {
      SafelySurrenderedBabies expected = createSafelySurrenderedBabies();
      SafelySurrenderedBabies actual = dao.find("AaiU7IW0Rt");
      Assert.assertEquals(expected, actual);
    });
  }

  private static SafelySurrenderedBabies createSafelySurrenderedBabies() {
    SafelySurrenderedBabies ssb = new SafelySurrenderedBabies();
    ssb.setChildClientId("AaiU7IW0Rt");
    ssb.setBraceletIdInfoCode("U");
    ssb.setCommentDescription("desc");
    ssb.setCpsNotofiedDate(LocalDate.of(2018, 01, 01));
    ssb.setCpsNotofiedIndicator(Boolean.TRUE);
    ssb.setCpsNotofiedTime(LocalTime.of(10, 01));
    ssb.setMedicalQuestionnaireCode("U");
    ssb.setQuestionnaireReceivedDate(LocalDate.of(2018, 01, 02));
    ssb.setRelationToClient((short) 1592);
    ssb.setSpecialProjectReferral("BaiU7IW0Rt");
    ssb.setSurrenderedByName("Name");
    ssb.setSurrenderedDate(LocalDate.of(2018, 02, 03));
    ssb.setSurrenderedTime(LocalTime.of(07, 01));
    ssb.setLastUpdateTime(LocalDateTime.of(2017, 11, 01, 12, 53, 07, 580225));
    return ssb;
  }
}
