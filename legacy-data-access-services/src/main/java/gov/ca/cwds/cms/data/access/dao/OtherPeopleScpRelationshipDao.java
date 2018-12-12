package gov.ca.cwds.cms.data.access.dao;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.XaDasSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.OtherPeopleScpRelationship;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class OtherPeopleScpRelationshipDao extends BaseDaoImpl<OtherPeopleScpRelationship> {

  @Inject
  public OtherPeopleScpRelationshipDao(@XaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

