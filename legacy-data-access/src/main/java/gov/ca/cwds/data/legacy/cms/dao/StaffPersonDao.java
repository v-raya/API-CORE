package gov.ca.cwds.data.legacy.cms.dao;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import gov.ca.cwds.data.BaseDaoImpl;
import gov.ca.cwds.data.legacy.cms.entity.StaffPerson;
import gov.ca.cwds.data.legacy.cms.entity.facade.ClientIdsByStaff;
import gov.ca.cwds.data.legacy.cms.entity.facade.StaffBySupervisor;
import gov.ca.cwds.inject.CmsSessionFactory;
import gov.ca.cwds.util.Require;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
   * Returns the immutable map of staff person identifier to set of client identifiers that are
   * assigned to the staff person.
   *
   * <p>N.B. If there are no clients assigned to a particular staff person then there will be an
   * empty set mapped to them.
   *
   * @param staffIds - identifiers of the Staff Persons.
   * @param date - the actual date
   * @return Returns the immutable map of staff person identifier to set of client identifiers that
   *     are assigned to the staff person.
   */
  public Map<String, Set<String>> findClientIdsByStaffIds(
      final Collection<String> staffIds, final LocalDate date) {
    Require.requireNotNullAndNotEmpty(staffIds);
    final List<ClientIdsByStaff> clientIdsByStaffIds =
        currentSession()
            .getNamedNativeQuery(ClientIdsByStaff.NATIVE_FIND_CLIENT_IDS_BY_STAFF_IDS)
            .setResultSetMapping(ClientIdsByStaff.MAPPING)
            .setParameter(1, staffIds)
            .setParameter(2, date != null ? date : LocalDate.now())
            .getResultList();
    final Map<String, Set<String>> results =
        clientIdsByStaffIds
            .stream()
            .collect(
                Collectors.groupingBy(
                    ClientIdsByStaff::getStaffIdentifier,
                    Collectors.mapping(ClientIdsByStaff::getClientIdentifier, Collectors.toSet())));
    // add default empty Set for staffIds that have no clients
    staffIds
        .stream()
        .filter(staffId -> !results.containsKey(staffId))
        .forEach(staffId -> results.put(staffId, Collections.emptySet()));
    return ImmutableMap.copyOf(results);
  }
}
