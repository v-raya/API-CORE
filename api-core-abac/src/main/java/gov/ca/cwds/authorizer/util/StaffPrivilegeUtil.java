package gov.ca.cwds.authorizer.util;


import gov.ca.cwds.authorizer.StaffPrivilegeType;
import gov.ca.cwds.security.realm.PerryAccount;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author CWDS TPT-3 Team
 */
public final class StaffPrivilegeUtil {

  private static final String CWS_CASE_MANAGEMENT_SYSTEM = "CWS Case Management System";
  private static final String STATE_OF_CALIFORNIA = "State of California";
  private static final String SENSITIVE_PERSONS = "Sensitive Persons";
  private static final String SEALED = "Sealed";
  private static final String RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT = "Resource Mgmt Placement Facility Maint";

  private StaffPrivilegeUtil() {
  }

  // TODO(dd): Consider to move this logic to drools
  public static Set<StaffPrivilegeType> toStaffPersonPrivilegeTypes(final PerryAccount perryAccount) {
    final Set<String> privileges = perryAccount.getPrivileges();
    if (privileges == null || privileges.isEmpty()) {
      return Collections.emptySet();
    }

    final String countyName = perryAccount.getCountyName();
    final Set<StaffPrivilegeType> results = new HashSet<>();
    for (final String privilege : privileges) {
      if (CWS_CASE_MANAGEMENT_SYSTEM.equals(privilege)) {
        results.add(StaffPrivilegeType.SOCIAL_WORKER_ONLY);
        continue;
      }

      if (RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT.equals(privilege)) {
        results.add(StaffPrivilegeType.RESOURCE_MGMT_PLACEMENT_FACILITY_MAINT);
        continue;
      }

      if (STATE_OF_CALIFORNIA.equals(countyName)) {
        addStateLevelPrivilegeIfNeeded(results, privilege);
      } else {
        addCountyLevelPrivilegeIfNeeded(results, privilege);
      }
    }
    return results;
  }

  private static void addStateLevelPrivilegeIfNeeded(final Set<StaffPrivilegeType> results,
      final String privilege) {
    if (SENSITIVE_PERSONS.equals(privilege)) {
      results.add(StaffPrivilegeType.STATE_SENSITIVE);
    } else if (SEALED.equals(privilege)) {
      results.add(StaffPrivilegeType.STATE_SEALED);
    }
  }

  private static void addCountyLevelPrivilegeIfNeeded(final Set<StaffPrivilegeType> results,
      final String privilege) {
    if (SENSITIVE_PERSONS.equals(privilege)) {
      results.add(StaffPrivilegeType.COUNTY_SENSITIVE);
    } else if (SEALED.equals(privilege)) {
      results.add(StaffPrivilegeType.COUNTY_SEALED);
    }
  }
}
