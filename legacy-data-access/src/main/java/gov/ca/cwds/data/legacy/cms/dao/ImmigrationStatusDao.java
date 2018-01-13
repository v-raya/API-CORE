package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ImmigrationStatus;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class ImmigrationStatusDao extends BaseDaoImpl<ImmigrationStatus> {

  @Inject
  public ImmigrationStatusDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<ImmigrationStatus> findAll() {
    return queryImmutableList(ImmigrationStatus.NQ_ALL);
  }
}