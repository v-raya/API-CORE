package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.FCEligibility;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS TPT-3 Team
 */
public class FCEligibilityDao extends BaseDaoImpl<FCEligibility> {

  @Inject
  public FCEligibilityDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<FCEligibility> findByChildClientId(String childClientId) {
    Session session = this.grabSession();
    final Query<FCEligibility> query =
        session.createNamedQuery(FCEligibility.FIND_BY_CLIENT, FCEligibility.class);
    query.setParameter("childId", childClientId);
    ImmutableList.Builder<FCEligibility> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
