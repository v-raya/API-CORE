package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * @author CWDS TPT-3 Team
 */
public class PlacementEpisodeDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final String CLIENT_ID = "AazXkWY06s";

  private PlacementEpisodeDao dao = null;

  @Before
  public void before() {
    dao = new PlacementEpisodeDao(sessionFactory);
  }

  @Test
  public void testFindByChildClientId() throws Exception {
    cleanAllAndInsert("/dbunit/PlacementEpisodes.xml");

    executeInTransaction(
        sessionFactory,
        sessionFactory -> {
          List<PlacementEpisode> placementEpisodes = dao.findByClientId(CLIENT_ID);
          assertEquals(1, placementEpisodes.size());
          PlacementEpisode placementEpisode = placementEpisodes.get(0);
          String clientId = placementEpisode.getFkclientT();
          assertEquals(CLIENT_ID, clientId);
        });
  }
}
