package gov.ca.cwds.data.legacy.cms.dao;

import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ClientScpEthnicityDaoTest extends BaseCwsCmsInMemoryPersistenceTest {
    private ClientScpEthnicityDao dao = null;

    @Before
    public void before() throws Exception {
        dao = new ClientScpEthnicityDao(sessionFactory);
    }

    @Test
    public void testFindEthnicitiesByClient() throws Exception {
        cleanAllAndInsert("/dbunit/ClientScpEthnicity.xml");

        executeInTransaction(
                sessionFactory,
                (sessionFactory) -> {
                    List<ClientScpEthnicity> result1 = dao.findEthnicitiesByClient("05gLqgG10R");
                    assertNotNull(result1);
                    assertTrue(result1.size() == 2);

                    List<ClientScpEthnicity> result2 = dao.findEthnicitiesByClient("4NWalZx0Wl");
                    assertNotNull(result2);
                    assertTrue(result2.size() == 1);

                    List<ClientScpEthnicity> result3 = dao.findEthnicitiesByClient("05gLqgG00R");
                    assertNotNull(result3);
                    assertTrue(result3.size() == 1);

                    List<ClientScpEthnicity> result4 = dao.findEthnicitiesByClient("06KlkhtAWW");
                    assertNotNull(result4);
                    assertTrue(result4.size() == 0);
                });
    }
}
