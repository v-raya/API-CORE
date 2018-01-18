package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class ClientRelationshipTypeDao extends BaseDaoImpl<ClientRelationshipType> {

  @Inject
  public ClientRelationshipTypeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<ClientRelationshipType> findAll() {
    return queryImmutableList(ClientRelationshipType.NQ_ALL);
  }
}
