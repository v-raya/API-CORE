package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class DeliveredServiceDaoTest extends BaseCwsCmsInMemoryPersistenceTest {
  public static final String CLIENT_ID = "00xVOYD00c";
  public static final String WRONG_CLIENT_ID = "00x0000000";

  private DeliveredServiceDao deliveredServiceDao;
  private ClientDao clientDao;

  @Before
  public void before() {
    deliveredServiceDao = new DeliveredServiceDao(sessionFactory);
    clientDao = new ClientDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/Client/update/DeliveredService.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory1 -> {
          Client client = clientDao.find(CLIENT_ID);
          assertNotNull(client);
          assertEquals(CLIENT_ID, client.getIdentifier());
        });

    executeInTransaction(
        sessionFactory,
        sessionFactory1 -> {
          List<DeliveredService> deliveredService = deliveredServiceDao.findByClientId(CLIENT_ID);
          assertNotNull(deliveredService);
          assertEquals(1, deliveredService.size());
          assertEquals(CLIENT_ID, deliveredService.get(0).getIndividualId());
        });
  }

  @Test
  public void testFindByWrongChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/Client/update/DeliveredService.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory1 -> {
          Client childClient = clientDao.find(WRONG_CLIENT_ID);
          assertNull(childClient);
        });

    executeInTransaction(
        sessionFactory,
        sessionFactory1 -> {
          List<DeliveredService> deliveredService =
              deliveredServiceDao.findByClientId(WRONG_CLIENT_ID);
          assertEquals(0, deliveredService.size());
        });
  }
}
