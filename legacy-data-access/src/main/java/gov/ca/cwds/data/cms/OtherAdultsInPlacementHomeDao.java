package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.OtherAdultsInPlacementHome;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class OtherAdultsInPlacementHomeDao extends BaseDaoImpl<OtherAdultsInPlacementHome> {

  /**
   * Constructor
   *
   * @param sessionFactory The session factory
   */
  @Inject
  public OtherAdultsInPlacementHomeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}
