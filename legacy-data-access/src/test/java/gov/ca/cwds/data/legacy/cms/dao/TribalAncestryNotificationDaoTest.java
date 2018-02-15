package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class TribalAncestryNotificationDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  public static final String CHILD_CLIENT_ID = "Ek694Ij0Wl";

  private TribalAncestryNotificationDao dao = null;

  @Before
  public void before() {
    dao = new TribalAncestryNotificationDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/TribalAncestryNotifications.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory -> {
          List<TribalAncestryNotification> notifications = dao.findByChildClientId(CHILD_CLIENT_ID);
          assertEquals(1, notifications.size());
          TribalAncestryNotification notification = notifications.get(0);
          String childClientId = notification.getChildClient().getIdentifier();
          assertEquals(CHILD_CLIENT_ID, childClientId);
        });
  }
}
