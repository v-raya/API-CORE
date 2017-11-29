package gov.ca.cwds.cms.data.access.dao.substitutecareprovider;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.SubstituteCareProviderSessionFactory;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CountyOwnership;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class CountyOwnershipDao extends BaseDaoImpl<CountyOwnership> {

  @Inject
  public CountyOwnershipDao(@SubstituteCareProviderSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
