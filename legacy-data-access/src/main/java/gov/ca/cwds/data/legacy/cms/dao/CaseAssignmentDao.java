package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.CaseAssignment;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class CaseAssignmentDao extends BaseDaoImpl<CaseAssignment> {

  @Inject
  public CaseAssignmentDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

}
