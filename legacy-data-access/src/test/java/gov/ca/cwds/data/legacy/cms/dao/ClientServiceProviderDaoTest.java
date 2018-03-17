package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class ClientServiceProviderDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String CLIENT_ID = "DYPp05u0ND";

  private ClientServiceProviderDao dao = null;

  @Before
  public void before() {
    dao = new ClientServiceProviderDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/ClientServiceProviders.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory -> {
          List<ClientServiceProvider> clientServiceProviders = dao.findByClientId(CLIENT_ID);
          assertEquals(1, clientServiceProviders.size());
          ClientServiceProvider clientServiceProvider = clientServiceProviders.get(0);
          String childClientId = clientServiceProvider.getClient().getIdentifier();
          assertEquals(CLIENT_ID, childClientId);
        });
  }
}
