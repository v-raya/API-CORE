package gov.ca.cwds.cms.data.access.dao.substitutecareprovider;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.SubstituteCareProviderSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PhoneContactDetail;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class PhoneContactDetailDao extends BaseDaoImpl<PhoneContactDetail> {

  @Inject
  public PhoneContactDetailDao(@SubstituteCareProviderSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
