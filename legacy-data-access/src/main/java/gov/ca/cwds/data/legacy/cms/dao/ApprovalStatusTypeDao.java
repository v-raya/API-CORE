package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ApprovalStatusType;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS CASE API Team
 */
public class ApprovalStatusTypeDao extends BaseDaoImpl<ApprovalStatusType> {

  @Inject
  public ApprovalStatusTypeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<ApprovalStatusType> findAll() {
    Session session = this.grabSession();
    final Query<ApprovalStatusType> query =
        session.createNamedQuery(ApprovalStatusType.NQ_ALL, ApprovalStatusType.class);
    ImmutableList.Builder<ApprovalStatusType> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
