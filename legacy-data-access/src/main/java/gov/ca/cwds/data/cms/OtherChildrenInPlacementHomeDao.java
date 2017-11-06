package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.OtherChildrenInPlacementHome;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class OtherChildrenInPlacementHomeDao extends BaseDaoImpl<OtherChildrenInPlacementHome> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public OtherChildrenInPlacementHomeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
