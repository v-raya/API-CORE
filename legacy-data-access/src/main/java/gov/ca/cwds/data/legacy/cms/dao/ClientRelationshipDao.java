package gov.ca.cwds.data.legacy.cms.dao;

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

  public List<ClientRelationship> findRelationshipsFromLeftSide(String leftSideId,
      LocalDate date) {
    Require.requireNotNullAndNotEmpty(leftSideId);
    Require.requireNotNullAndNotEmpty(date);

    final List<ClientRelationship> relationships = currentSession()
        .createNamedQuery(ClientRelationship.NQ_FIND_CURRENT_RELATIONSHIPS_FROM_LEFT_SIDE,
            ClientRelationship.class)
        .setParameter(ClientRelationship.NQ_PARAM_LEFT_SIDE_ID, leftSideId)
        .setParameter(ClientRelationship.NQ_PARAM_CURRENT_DATE, date)
        .list();

    return ImmutableList.copyOf(relationships);
  }

  public List<ClientRelationship> findCurrentRelationshipsFromLeftSide(String leftSideId) {
    return findRelationshipsFromLeftSide(leftSideId, LocalDate.now());
  }

  public List<ClientRelationship> findRelationshipsFromRightSide(String rightSideId,
      LocalDate date) {
    Require.requireNotNullAndNotEmpty(rightSideId);
    Require.requireNotNullAndNotEmpty(date);

    final List<ClientRelationship> relationships = currentSession()
        .createNamedQuery(ClientRelationship.NQ_FIND_CURRENT_RELATIONSHIPS_FROM_RIGHT_SIDE,
            ClientRelationship.class)
        .setParameter(ClientRelationship.NQ_PARAM_RIGHT_SIDE_ID, rightSideId)
        .setParameter(ClientRelationship.NQ_PARAM_CURRENT_DATE, date)
        .list();

    return ImmutableList.copyOf(relationships);
  }

  public List<ClientRelationship> findCurrentRelationshipsFromRightSide(String rightSideId) {
    return findRelationshipsFromRightSide(rightSideId, LocalDate.now());
  }
}
