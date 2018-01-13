package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Religion;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;

/** @author CWDS CALS API Team */
public class ReligionDao extends BaseDaoImpl<Religion> {

  @Inject
  public ReligionDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<Religion> findAll() {
    return queryImmutableList(Religion.NQ_ALL);
  }
}
