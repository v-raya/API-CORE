package gov.ca.cwds.data.legacy.cms.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;

import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.FacilityType;
import gov.ca.cwds.inject.CmsSessionFactory;

/**
 * @author CWDS CALS API Team
 */
public class FacilityTypeDao extends BaseDaoImpl<FacilityType> {

  @Inject
  public FacilityTypeDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  @Override
  public List<FacilityType> findAll() {
    Session session = this.grabSession();
    final Query<FacilityType> query =
        session.createNamedQuery(FacilityType.NQ_ALL, FacilityType.class);
    ImmutableList.Builder<FacilityType> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }

}
