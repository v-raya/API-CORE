package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Language;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;

/**
 * DAO for special projects
 * 
 * @author CWDS API Team
 */
public class SpecialProjectDao extends BaseDaoImpl<Language> {

  @Inject
  public SpecialProjectDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Find active SSB special projects for given government entity.
   * 
   * @param governmentEntity Government entity for which to find SSB special project
   * @return The active SSB special projects for given government entity, returns null if not found.
   */
  public List<SpecialProject> findActiveSSBByGovernmentEntity(Short governmentEntity) {
    Require.requireNotNullAndNotEmpty(governmentEntity);

    final List<SpecialProject> specialProjects = currentSession()
        .createNamedQuery(SpecialProject.FIND_ACTIVE_SSB_BY_GOVERNMENT_ENTITY, SpecialProject.class)
        .setParameter(SpecialProject.PARAM_GOVERNMENT_ENTITY, governmentEntity).list();

    return ImmutableList.copyOf(specialProjects);
  }
}
