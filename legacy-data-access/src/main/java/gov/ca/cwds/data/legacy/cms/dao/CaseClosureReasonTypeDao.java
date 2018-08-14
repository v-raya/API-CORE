package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.CaseClosureReasonType;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS CASE API Team
 */
public class CaseClosureReasonTypeDao extends BaseDaoImpl<CaseClosureReasonType> {

  @Inject
  public CaseClosureReasonTypeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<CaseClosureReasonType> findAll() {
    Session session = this.grabSession();
    final Query<CaseClosureReasonType> query =
        session.createNamedQuery(CaseClosureReasonType.NQ_ALL, CaseClosureReasonType.class);
    ImmutableList.Builder<CaseClosureReasonType> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
