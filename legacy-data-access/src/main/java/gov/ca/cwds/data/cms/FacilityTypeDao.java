package gov.ca.cwds.data.cms;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.persistence.cms.FacilityType;
import gov.ca.cwds.inject.CmsSessionFactory;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


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
    Session session = this.getSessionFactory().getCurrentSession();
    Query<FacilityType> query = session.createNamedQuery(FacilityType.NQ_ALL, FacilityType.class);
    ImmutableList.Builder<FacilityType> entities = new ImmutableList.Builder<>();
    entities.addAll(query.list());
    return entities.build();
  }

}
