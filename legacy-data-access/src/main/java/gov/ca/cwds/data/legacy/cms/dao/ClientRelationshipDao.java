package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.NQ_FIND_CURRENT_RELATIONSHIPS_FROM_LEFT_SIDE;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.NQ_FIND_CURRENT_RELATIONSHIPS_FROM_RIGHT_SIDE;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.NQ_PARAM_CURRENT_DATE;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.SessionFactory;

public class ClientRelationshipDao extends BaseDaoImpl<ClientRelationship> {

  @Inject
  public ClientRelationshipDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<ClientRelationship> findRightRelationships(String clientId,
      LocalDate localDate) {
    return findRelationships(NQ_FIND_CURRENT_RELATIONSHIPS_FROM_LEFT_SIDE, clientId, localDate);
  }

  public List<ClientRelationship> findCurrentRightRelationships(String clientId) {
    return findRightRelationships(clientId, LocalDate.now());
  }

  public List<ClientRelationship> findLeftRelationships(String clientId,
      LocalDate localDate) {
    return findRelationships(NQ_FIND_CURRENT_RELATIONSHIPS_FROM_RIGHT_SIDE, clientId, localDate);
  }

  public List<ClientRelationship> findCurrentLeftRelationships(String clientId) {
    return findLeftRelationships(clientId, LocalDate.now());
  }

  private List<ClientRelationship> findRelationships(String queryName, String clientId,
      LocalDate localDate
  ) {
    Require.requireNotNullAndNotEmpty(clientId);
    Require.requireNotNullAndNotEmpty(localDate);

    final List<ClientRelationship> relationships = currentSession()
        .createNamedQuery(queryName, ClientRelationship.class)
        .setParameter(CLIENT_ID, clientId)
        .setParameter(NQ_PARAM_CURRENT_DATE, localDate)
        .list();

    return ImmutableList.copyOf(relationships);
  }
}
