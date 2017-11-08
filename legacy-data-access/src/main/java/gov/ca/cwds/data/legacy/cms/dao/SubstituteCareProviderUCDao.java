package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProviderUc;
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