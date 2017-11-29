package gov.ca.cwds.cms.data.access.dao.substitutecareprovider;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.SubstituteCareProviderSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.OutOfStateCheck;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class OutOfStateCheckDao extends BaseDaoImpl<OutOfStateCheck> {

  @Inject
  public OutOfStateCheckDao(@SubstituteCareProviderSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
