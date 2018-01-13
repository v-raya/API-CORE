package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.Case;
import gov.ca.cwds.data.legacy.cms.entity.CaseAssignment;
import gov.ca.cwds.data.legacy.cms.entity.CaseLoad;
import gov.ca.cwds.data.legacy.cms.entity.CaseLoadWeighting;
import gov.ca.cwds.data.legacy.cms.entity.StaffPerson;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 * DAO for {@link Case}.
 * 
 * @author CWDS API Team
 */
public class CaseDao extends CrudsDaoImpl<Case> {

  /**
   * Constructor
   * 
   * @param sessionFactory The sessionFactory
   */
  @Inject
  public CaseDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Returns cases which have relation to {@link StaffPerson}
   * via {@link CaseAssignment}, {@link CaseLoad} and {@link CaseLoadWeighting}
   * entities. Also the {@link CaseAssignment} is active, which means {@param activeDate} is between
   * startDate and endDate of the assignment.
   *
   * @param staffId - Identifier of Staff Person who can work on the returned cases.
   * @param activeDate - The returned cases assignment will be active on this date.
   * As usual this param is a current date.
   * @return Cases with active assignments that can be managed by requested staff person
   */
  public Collection<Case> findByStaffIdAndActiveDate(final String staffId, final LocalDate activeDate) {
    Require.requireNotNullAndNotEmpty(staffId);

    final List<Case> cases = currentSession()
        .createNamedQuery(Case.NQ_FIND_ACTIVE_BY_STAFF_ID, Case.class)
        .setParameter(Case.NQ_PARAM_STAFF_ID, staffId)
        .setParameter(Case.NQ_PARAM_ACTIVE_DATE, activeDate != null ? activeDate : LocalDate.now())
        .list();

    return ImmutableList.<Case>builder().addAll(cases).build();
  }

  public List<Case> findActiveByClient(String clientId) {

    List<Case> cases = currentSession()
            .createNamedQuery(Case.NQ_FIND_ACTIVE_BY_CLIENT_ID, Case.class)
            .setParameter(Case.NQ_PARAM_CLIENT_ID, clientId)
            .list();

    return ImmutableList.<Case>builder().addAll(cases).build();
  }

  public List<Case> findClosedByClient(String clientId) {
    List<Case> cases = currentSession()
            .createNamedQuery(Case.NQ_FIND_CLOSED_BY_CLIENT_ID, Case.class)
            .setParameter(Case.NQ_PARAM_CLIENT_ID, clientId)
            .list();

    return ImmutableList.<Case>builder().addAll(cases).build();
  }
}
