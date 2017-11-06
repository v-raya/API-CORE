package gov.ca.cwds.data.cms;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.SubstituteCareProviderUc;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class SubstituteCareProviderUCDao extends BaseDaoImpl<SubstituteCareProviderUc> {

  @Inject
  public SubstituteCareProviderUCDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }
}