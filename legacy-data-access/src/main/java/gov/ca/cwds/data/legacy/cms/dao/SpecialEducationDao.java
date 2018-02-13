package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.SpecialEducation;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * @author CWDS TPT-3 Team
 */
public class SpecialEducationDao extends BaseDaoImpl<SpecialEducation> {

  @Inject
  public SpecialEducationDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<SpecialEducation> findByClientId(String clientId) {
    Session session = this.getSessionFactory().getCurrentSession();
    Query<SpecialEducation> query =
        session.createNamedQuery(SpecialEducation.FIND_BY_CLIENT_ID, SpecialEducation.class);
    query.setParameter(SpecialEducation.PARAM_CLIENT_ID, clientId);
    ImmutableList.Builder<SpecialEducation> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
