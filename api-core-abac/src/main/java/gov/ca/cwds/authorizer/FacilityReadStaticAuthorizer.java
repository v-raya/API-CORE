package gov.ca.cwds.authorizer;

import gov.ca.cwds.authorizer.util.StaffPrivilegeUtil;
import gov.ca.cwds.security.authorizer.StaticAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;

/**
 * Implementation of StaticAuthorizer.
 * @author CWDS TPT-2 Team
 */
public class FacilityReadStaticAuthorizer implements StaticAuthorizer {

  public static final String FACILITY_READ_PERMISSION = "facility:read";

  /**
   * Implementation of StaticAuthorizer.
   * @param perryAccount Perry Account
   * @param simpleAuthInfo Simple Authorization information
   */
  @Override
  public void authorize(PerryAccount perryAccount, SimpleAuthorizationInfo simpleAuthInfo) {
    if (perryAccount.getPrivileges().stream()
        .anyMatch(priv -> StaffPrivilegeUtil.RESOURCE_MANAGEMENT.equals(priv)
            || StaffPrivilegeUtil.CWS_CASE_MANAGEMENT_SYSTEM.equals(priv))) {
      simpleAuthInfo.addObjectPermission(new WildcardPermission(FACILITY_READ_PERMISSION));
    }
  }
}
