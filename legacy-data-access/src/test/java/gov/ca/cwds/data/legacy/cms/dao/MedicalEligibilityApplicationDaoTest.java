package gov.ca.cwds.data.legacy.cms.dao;

import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MedicalEligibilityApplicationDaoTest extends BaseCwsCmsInMemoryPersistenceTest {
    private MedicalEligibilityApplicationDao dao = null;

    @Before
    public void before() throws Exception {
        dao = new MedicalEligibilityApplicationDao(sessionFactory);
    }

    @Test
    public void testFindEthnicitiesByClient() throws Exception {
        cleanAllAndInsert("/dbunit/MedicalEligibilityApplication.xml");

        executeInTransaction(
                sessionFactory,
                (sessionFactory) -> {
                    List<MedicalEligibilityApplication> result1 = dao.findByChildClientId("AaiU7IW999");
                    assertNotNull(result1);
                    assertTrue(result1.size() == 1);

                    List<MedicalEligibilityApplication> result2 = dao.findByChildClientId("BashHMUq08");
                    assertNotNull(result2);
                    assertTrue(result2.size() == 0);

                    List<MedicalEligibilityApplication> result3 = dao.findByChildClientId("WqdhHTUq61");
                    assertNotNull(result3);
                    assertTrue(result3.size() == 2);
                });
    }

}
