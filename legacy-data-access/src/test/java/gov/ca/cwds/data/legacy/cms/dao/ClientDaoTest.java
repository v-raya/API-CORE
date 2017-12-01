package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import org.junit.Before;
import org.junit.Test;

public class ClientDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private ClientDao dao = null;

  @Before
  public void before() throws Exception {
    dao = new ClientDao(sessionFactory);
  }

  @Test
  public void testFind() throws Exception {
    cleanAllAndInsert("/dbunit/Clients.xml");

    executeInTransaction(sessionFactory,
        (sessionFactory) -> {
          Client client_1 = dao.find("AaiU7IW0Rt");
          assertNotNull(client_1);
          assertTrue(client_1 instanceof Client);

          Client client_2 = dao.find("AapJGAU04Z");
          assertNotNull(client_2);
          assertTrue(client_2 instanceof ChildClient);
        });
  }
}
