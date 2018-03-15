package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode.FIND_BY_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode.PARAM_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.List;
import org.hibernate.SessionFactory;

public class PlacementEpisodeDao extends BaseDaoImpl<PlacementEpisode> {

  @Inject
  public PlacementEpisodeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Returns immutable list of PlacementEpisodes belonging to the client with given ID.
   *
   * @param clientId  Client ID.
   * @return  PlacementEpisodes list.
   */
  public List<PlacementEpisode> findByClientId(String clientId) {

    Require.requireNotNullAndNotEmpty(clientId);

    final List<PlacementEpisode> details = currentSession()
        .createNamedQuery(FIND_BY_CLIENT_ID, PlacementEpisode.class)
        .setParameter(PARAM_CLIENT_ID, clientId)
        .list();

    return ImmutableList.copyOf(details);
  }
}
