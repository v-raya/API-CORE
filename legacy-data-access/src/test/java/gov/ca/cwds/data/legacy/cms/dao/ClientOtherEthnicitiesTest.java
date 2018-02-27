package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientOtherEthnicity;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.Set;
import org.hibernate.Session;
import org.junit.Test;

public class ClientOtherEthnicitiesTest extends BaseCwsCmsInMemoryPersistenceTest {

    @Test
    public void testFindEthnicitiesByClient() throws Exception {
        cleanAllAndInsert("/dbunit/ClientOtherEthnicities.xml");

        executeInTransaction(
                sessionFactory,
                (sessionFactory) -> {
                  Session session = sessionFactory.getCurrentSession();

                  Client client1 = session.load(Client.class, "05gLqgG10R");
                  Set<ClientOtherEthnicity> result1 = client1.getOtherEthnicities();
                  assertNotNull(result1);
                  assertTrue(result1.size() == 2);

                  Client client2 = session.load(Client.class, "fG5y8Sdg3F");
                  Set<ClientOtherEthnicity> result2 = client2.getOtherEthnicities();
                  assertNotNull(result2);
                  assertTrue(result2.size() == 0);
                });
    }
}
