package gov.ca.cwds.cms.data.access.dao.nonxa;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeDao;
import gov.ca.cwds.cms.data.access.inject.NonXaDasSessionFactory;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */
public class NonXaPlacementHomeDao extends PlacementHomeDao {

  private static final Logger LOG = LoggerFactory.getLogger(NonXaPlacementHomeDao.class);

  @Inject
  public NonXaPlacementHomeDao(@NonXaDasSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
