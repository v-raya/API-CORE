package gov.ca.cwds.data.legacy.cms.dao;

import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Ethnicity;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.SessionFactory;

/** @author CWDS CASE API Team */
public class EthnicityDao extends BaseDaoImpl<Ethnicity> {

  @Inject
  public EthnicityDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<Ethnicity> findAll() {
    return queryImmutableList(Ethnicity.NQ_ALL);
  }
}
