package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.PaternityDetail.FIND_PATERNITY_DETAILS_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.PaternityDetail.PARAM_CHILD_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.List;
import org.hibernate.SessionFactory;

public class PaternityDetailDao extends BaseDaoImpl<PaternityDetail> {

  @Inject
  public PaternityDetailDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<PaternityDetail> getPaternityDetailsByChildClientId(
      String childClientId) {

    Require.requireNotNullAndNotEmpty(childClientId);

    final List<PaternityDetail> details = currentSession()
        .createNamedQuery(FIND_PATERNITY_DETAILS_BY_CHILD_CLIENT_ID, PaternityDetail.class)
        .setParameter(PARAM_CHILD_CLIENT_ID, childClientId)
        .list();

    return ImmutableList.copyOf(details);
  }
}
