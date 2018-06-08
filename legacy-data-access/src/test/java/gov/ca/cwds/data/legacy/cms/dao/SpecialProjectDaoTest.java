package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;

public class SpecialProjectDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private SpecialProjectDao dao = null;

  @Before
  public void before() {
    dao = new SpecialProjectDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/SpecialProjects.xml");

    executeInTransaction(sessionFactory, sessionFactory -> {
      List<SpecialProject> specialProjects =
          dao.findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity((short) 1086);
      Assert.assertEquals(1, specialProjects.size());
      SpecialProject specialProject = specialProjects.get(0);

      Assert.assertEquals("S-Safely Surrendered Baby", specialProject.getName());
      Assert.assertEquals("Safely Surrendered Baby", specialProject.getDescription());
      Assert.assertEquals((short) 1086, specialProject.getGovernmentEntity().shortValue());
      Assert.assertEquals(Boolean.FALSE, specialProject.getArcassIndicator());
    });
  }
}
