package gov.ca.cwds.cms.data.access.dao.substitutecareprovider;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.inject.SubstituteCareProviderSessionFactory;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */

public class ScpSsaName3Dao extends SsaName3Dao {

  @Inject
  public ScpSsaName3Dao(@SubstituteCareProviderSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}

