package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.StaffPerson;
import gov.ca.cwds.data.legacy.cms.entity.facade.ClientCountByStaff;
import gov.ca.cwds.data.legacy.cms.entity.facade.StaffBySupervisor;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.util.Collection;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 * The DAO to work with StaffPerson entity
 *
 * @author CWDS CALS API Team
 */
public class StaffPersonDao extends BaseDaoImpl<StaffPerson> {

  @Inject
  public StaffPersonDao(@CmsSessionFactory SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Returns the immutable collection of {@link StaffBySupervisor} who are subordinates for a
   * supervisor {@link StaffPerson} by identifier of the supervisor. N.B. The returned objects are
   * POJOs, not of @Entity type.
   *
   * @param identifier - identifier of the supervisor Staff Person.
   * @return Returns the immutable collection of subordinates for the supervisor. N.B. The returned
   *     objects are not of @Entity type.
   */
  public Collection<StaffBySupervisor> findStaffBySupervisorId(final String identifier) {
    Require.requireNotNullAndNotEmpty(identifier);
    final List<StaffBySupervisor> staffList =
        currentSession()
            .getNamedNativeQuery(StaffBySupervisor.NATIVE_FIND_STAFF_BY_SUPERVISOR_ID)
            .setResultSetMapping(StaffBySupervisor.MAPPING_STAFF_BY_SUPERVISOR)
            .setParameter(1, identifier)
            .getResultList();
    return ImmutableList.copyOf(staffList);
  }

  /**
   * Returns the immutable collection of {@link ClientCountByStaff} that contains clients count for
   * each staff person from input staffIds list.
   *
   * <p>N.B. If there are no clients assigned to a particular staff id then there will be no
   * corresponding {@link ClientCountByStaff} object in returned collection.
   *
   * <p>N.B.2 The returned objects are POJOs, not of @Entity type.
   *
   * @param staffIds - identifier of the supervisor Staff Person.
   * @return Returns the immutable collection of {@link ClientCountByStaff} that contain clients
   *     count for each staff person from input staffIds list. N.B. The returned objects are not
   *     of @Entity type.
   */
  public Collection<ClientCountByStaff> countClientsByStaffIds(final Collection<String> staffIds) {
    Require.requireNotNullAndNotEmpty(staffIds);
    final List<ClientCountByStaff> results =
        currentSession()
            .getNamedNativeQuery(ClientCountByStaff.NATIVE_COUNT_CLIENTS_BY_STAFF_IDS)
            .setResultSetMapping(ClientCountByStaff.MAPPING_CLIENT_COUNT_BY_STAFF)
            .setParameter(1, staffIds)
            .getResultList();
    return ImmutableList.copyOf(results);
  }
}
