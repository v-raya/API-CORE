package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProjectReferral;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;

/**
 * DAO for {@link SpecialProjectReferral}.
 * 
 * @author CWDS API Team
 */
public class SpecialProjectReferralDao extends BaseDaoImpl<SpecialProjectReferral> {

  /**
   * Constructor.
   * 
   * @param sessionFactory the sessionFactory
   */
  @Inject
  public SpecialProjectReferralDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<SpecialProjectReferral> findSpecialProjectReferralsByReferralIdAndSpecialProjectId(String referralId,
      String specialProjectId) {
    Class<SpecialProjectReferral> entityClass = getEntityClass();
    String className = entityClass.getName();
    String queryName = className + ".findByReferralIdAndSpecialProjectId";
    Require.requireNotNullAndNotEmpty(referralId);
    Require.requireNotNullAndNotEmpty(specialProjectId);
    
    final List<SpecialProjectReferral> specialProjects = currentSession()
    .createNamedQuery(queryName, entityClass)
    .setParameter("referralId", referralId)
    .setParameter("specialProjectId", specialProjectId)
    .list();
    
    return ImmutableList.copyOf(specialProjects);
  }
  
}
