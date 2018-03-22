package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.OtherClientName;
import gov.ca.cwds.inject.CmsSessionFactory;
import org.hibernate.SessionFactory;

import java.util.List;

public class OtherClientNameDao extends BaseDaoImpl<OtherClientName> {

  @Inject
  public OtherClientNameDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<OtherClientName> findByClientId(String clientId) {
    List<OtherClientName> medicalEligibilityApplications =
        this.getSessionFactory()
            .getCurrentSession()
            .createNamedQuery(OtherClientName.NQ_FIND_BY_CLIENT_ID, OtherClientName.class)
            .setParameter(OtherClientName.NQ_PARAM_CLIENT_ID, clientId)
            .list();

    return ImmutableList.<OtherClientName>builder().addAll(medicalEligibilityApplications).build();
  }
}
