package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.FIND_TRIBAL_ANCESTRY_NOTIFICATION_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.PARAM_CHILD_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.List;
import org.hibernate.SessionFactory;

public class TribalAncestryNotificationDao extends BaseDaoImpl<PaternityDetail> {

  @Inject
  public TribalAncestryNotificationDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<TribalAncestryNotification> findByChildClientId(String childClientId) {

    Require.requireNotNullAndNotEmpty(childClientId);

    final List<TribalAncestryNotification> details = currentSession()
        .createNamedQuery(FIND_TRIBAL_ANCESTRY_NOTIFICATION_BY_CHILD_CLIENT_ID, TribalAncestryNotification.class)
        .setParameter(PARAM_CHILD_CLIENT_ID, childClientId)
        .list();

    return ImmutableList.copyOf(details);
  }
}
