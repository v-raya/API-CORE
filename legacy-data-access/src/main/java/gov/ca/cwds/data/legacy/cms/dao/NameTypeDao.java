package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;

/** @author CWDS CALS API Team */
public class NameTypeDao extends BaseDaoImpl<NameType> {

  @Inject
  public NameTypeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<NameType> findAll() {
    return queryImmutableList(NameType.NQ_ALL);
  }
}
