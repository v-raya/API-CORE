package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.SessionFactory;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SpecialProject;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;

/**
 * DAO for special projects.
 * 
 * @author CWDS API Team
 */
public class SpecialProjectDao extends BaseDaoImpl<SpecialProject> {

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
  public List<SpecialProject> findActiveSafelySurrenderedBabiesSpecialProjectByGovernmentEntity(
      Short governmentEntity) {
    
    Class<SpecialProject> entityClass = getEntityClass();
    String className = entityClass.getName();
    String queryName = className + ".findActiveSSBByGovernmentEntity";
  
    Require.requireNotNullAndNotEmpty(governmentEntity);

    final List<SpecialProject> specialProjects = currentSession()
        .createNamedQuery(queryName, entityClass)
        .setParameter(SpecialProject.PARAM_GOVERNMENT_ENTITY, governmentEntity).list();

    return ImmutableList.copyOf(specialProjects);
  }
  
  /**
   * find SpecialProject by government entity and special project name
   * 
   * @param governmentEntityType - government entity type
   * @param name - special project name
   * @return - list of special projects matching parameters
   */
  public List<SpecialProject> findSpecialProjectByGovernmentEntityAndName(String name, 
      Short governmentEntityType) {
    Class<SpecialProject> entityClass = getEntityClass();
    String className = entityClass.getName();
    String queryName = className + ".findByProjectName";
    
    Require.requireNotNullAndNotEmpty(governmentEntityType);

    final List<SpecialProject> specialProjects = currentSession()
      .createNamedQuery(queryName, entityClass)
      .setParameter(SpecialProject.PARAM_GOVERNMENT_ENTITY, governmentEntityType)
      .setParameter(SpecialProject.PARAM_NAME, name)
      .list();
    return ImmutableList.copyOf(specialProjects);
    }
}
