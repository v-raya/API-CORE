package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.NQ_FIND_RELATIONSHIPS_BY_SECONDARY_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ClientRelationship.NQ_FIND_RELATIONSHIPS_BY_PRIMARY_CLIENT_ID;
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

  public List<ClientRelationship> findRelationshipsBySecondaryClientId(
      String leftSideClientId, LocalDate localDate) {
    return findRelationships(
        NQ_FIND_RELATIONSHIPS_BY_SECONDARY_CLIENT_ID, leftSideClientId, localDate);
  }

  public List<ClientRelationship> findRelationshipsByPrimaryClientId(
      String rightSideClientId, LocalDate localDate) {
    return findRelationships(
        NQ_FIND_RELATIONSHIPS_BY_PRIMARY_CLIENT_ID, rightSideClientId, localDate);
  }

  private List<ClientRelationship> findRelationships(
      String queryName, String clientId, LocalDate localDate) {
    Require.requireNotNullAndNotEmpty(clientId);
    Require.requireNotNullAndNotEmpty(localDate);

    final List<ClientRelationship> relationships =
        currentSession()
            .createNamedQuery(queryName, ClientRelationship.class)
            .setParameter(CLIENT_ID, clientId)
            .setParameter(NQ_PARAM_CURRENT_DATE, localDate)
            .list();

    return ImmutableList.copyOf(relationships);
  }
}
