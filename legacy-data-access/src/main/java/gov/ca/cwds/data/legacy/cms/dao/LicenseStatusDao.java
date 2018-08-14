package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.LicenseStatus;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class LicenseStatusDao extends BaseDaoImpl<LicenseStatus> {

  @Inject
  public LicenseStatusDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<LicenseStatus> findAll() {
    Session session = this.grabSession();
    final Query<LicenseStatus> query =
        session.createNamedQuery(LicenseStatus.NQ_ALL, LicenseStatus.class);
    ImmutableList.Builder<LicenseStatus> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }
}
