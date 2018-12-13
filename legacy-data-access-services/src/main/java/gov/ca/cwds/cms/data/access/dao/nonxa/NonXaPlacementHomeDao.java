package gov.ca.cwds.cms.data.access.dao.nonxa;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeDao;
import gov.ca.cwds.cms.data.access.inject.NonXaDasSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team.
 */
public class NonXaPlacementHomeDao extends PlacementHomeDao {

  @Inject
  public NonXaPlacementHomeDao(@NonXaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
