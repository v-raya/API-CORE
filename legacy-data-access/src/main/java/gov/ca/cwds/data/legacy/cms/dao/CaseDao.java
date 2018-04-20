package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.CrudsDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.Case;
import gov.ca.cwds.data.legacy.cms.entity.CaseAssignment;
import gov.ca.cwds.data.legacy.cms.entity.CaseLoad;
import gov.ca.cwds.data.legacy.cms.entity.CaseLoadWeighting;
import gov.ca.cwds.data.legacy.cms.entity.StaffPerson;
import gov.ca.cwds.data.legacy.cms.entity.facade.CaseByStaff;
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
   * Returns cases which have relation to {@link StaffPerson} via {@link CaseAssignment}, {@link
   * CaseLoad} and {@link CaseLoadWeighting} entities. Also the {@link CaseAssignment} is active,
   * which means param activeDate is between startDate and endDate of the assignment.
   *
   * @param staffId - Identifier of Staff Person who can work on the returned cases.
   * @param activeDate - The returned cases assignment will be active on this date. As usual this
   *     param is a current date.
   * @return Cases with active assignments that can be managed by requested staff person. N.B. The
   *     returned objects are not of @Entity type.
   */
  public Collection<CaseByStaff> nativeFindByStaffIdAndActiveDate(
      final String staffId, final LocalDate activeDate) {
    Require.requireNotNullAndNotEmpty(staffId);

    final LocalDate date = activeDate != null ? activeDate : LocalDate.now();
    final List<CaseByStaff> cases =
        currentSession()
            .getNamedNativeQuery(CaseByStaff.NATIVE_FIND_CASES_BY_STAFF_ID)
            .setResultSetMapping(CaseByStaff.MAPPING_CASE_BY_STAFF)
            .setParameter(1, staffId)
            .setParameter(2, date)
            .setParameter(3, date)
            .getResultList();

    return ImmutableList.copyOf(cases);
  }

  public List<Case> findActiveByClient(String clientId) {

    List<Case> cases =
        currentSession()
            .createNamedQuery(Case.NQ_FIND_ACTIVE_BY_CLIENT_ID, Case.class)
            .setParameter(Case.NQ_PARAM_CLIENT_ID, clientId)
            .list();

    return ImmutableList.<Case>builder().addAll(cases).build();
  }

  public List<Case> findClosedByClient(String clientId) {
    List<Case> cases =
        currentSession()
            .createNamedQuery(Case.NQ_FIND_CLOSED_BY_CLIENT_ID, Case.class)
            .setParameter(Case.NQ_PARAM_CLIENT_ID, clientId)
            .list();

    return ImmutableList.<Case>builder().addAll(cases).build();
  }
}
