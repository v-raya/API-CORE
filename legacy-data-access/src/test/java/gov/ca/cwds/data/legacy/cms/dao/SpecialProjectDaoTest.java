package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.security.utils.PrincipalUtils;

public class SpecialProjectDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private SpecialProjectDao dao = null;

  @Override
  @Before
  public void setup() throws Exception {
    super.setup();
    dao = new SpecialProjectDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/SpecialProjects.xml");

    executeInTransaction(sessionFactory, sessionFactory -> {
      final List<SpecialProject> specialProjects =
          dao.findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity((short) 1086);
      Assert.assertEquals(1, specialProjects.size());
      final SpecialProject specialProject = specialProjects.get(0);

      Assert.assertEquals("S-Safely Surrendered Baby", specialProject.getName());
      Assert.assertEquals("Safely Surrendered Baby", specialProject.getProjectDescription());
      Assert.assertEquals((short) 1086, specialProject.getGovernmentEntityType().shortValue());
      Assert.assertEquals(Boolean.FALSE, specialProject.getArrchiveAssociationIndicator());
    });
  }

  @Test
  public void testFindByReferralIdAndSpecialProjectId() throws Exception {
    cleanAllAndInsert("/dbunit/SpecialProjects.xml");

    executeInTransaction(sessionFactory, sessionFactor -> {
      final List<SpecialProject> specialProjects =
          dao.findSpecialProjectByGovernmentEntityAndName("S-CSEC Referral", (short) 1086);
      Assert.assertEquals(1, specialProjects.size());
      final SpecialProject specialProject = specialProjects.get(0);

      Assert.assertEquals("S-CSEC Referral", specialProject.getName());
      Assert.assertEquals("CSEC special project referral", specialProject.getProjectDescription());
      Assert.assertEquals((short) 1086, specialProject.getGovernmentEntityType().shortValue());
      Assert.assertEquals(Boolean.FALSE, specialProject.getArrchiveAssociationIndicator());
    });
  }

}
