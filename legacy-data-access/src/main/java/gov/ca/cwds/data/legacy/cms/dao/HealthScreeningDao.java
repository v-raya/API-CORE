package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.HealthScreening.FIND_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.HealthScreening.PARAM_CHILD_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.HealthScreening;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.List;
import org.hibernate.SessionFactory;

public class HealthScreeningDao extends BaseDaoImpl<HealthScreening> {

  @Inject
  public HealthScreeningDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<HealthScreening> findByChildClientId(String childClientId) {

    Require.requireNotNullAndNotEmpty(childClientId);

    final List<HealthScreening> details = currentSession()
        .createNamedQuery(FIND_BY_CHILD_CLIENT_ID, HealthScreening.class)
        .setParameter(PARAM_CHILD_CLIENT_ID, childClientId)
        .list();

    return ImmutableList.copyOf(details);
  }
}
