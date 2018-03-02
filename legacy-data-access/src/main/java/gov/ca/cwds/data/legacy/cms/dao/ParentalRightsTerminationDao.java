package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination.FIND_TERMINATION_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination.PARAM_CHILD_CLIENT_ID;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.List;
import org.hibernate.SessionFactory;

public class ParentalRightsTerminationDao extends BaseDaoImpl<ParentalRightsTermination> {

  @Inject
  public ParentalRightsTerminationDao(@CmsSessionFactory final SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  public List<ParentalRightsTermination> findByChildClientId(
      String childClientId) {

    Require.requireNotNullAndNotEmpty(childClientId);

    final List<ParentalRightsTermination> terminations = currentSession()
        .createNamedQuery(FIND_TERMINATION_BY_CHILD_CLIENT_ID, ParentalRightsTermination.class)
        .setParameter(PARAM_CHILD_CLIENT_ID, childClientId)
        .list();

    return ImmutableList.copyOf(terminations);
  }
}
