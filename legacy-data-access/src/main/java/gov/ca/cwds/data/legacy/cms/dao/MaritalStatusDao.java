package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.MaritalStatus;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;

/** @author CWDS CALS API Team */
public class MaritalStatusDao extends BaseDaoImpl<MaritalStatus> {

  @Inject
  public MaritalStatusDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<MaritalStatus> findAll() {
    return queryImmutableList(MaritalStatus.NQ_ALL);
  }
}
