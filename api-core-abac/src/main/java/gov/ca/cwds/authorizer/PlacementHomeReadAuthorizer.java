package gov.ca.cwds.authorizer;

import gov.ca.cwds.security.authorizer.StaticAuthorizer;
import gov.ca.cwds.security.realm.PerryAccount;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;

public class PlacementHomeReadAuthorizer implements StaticAuthorizer {
  public static final String PLACEMENT_HOME_READ_PERMISSION = "placementHome:read";

  @Override
  public void authorize(PerryAccount perryAccount, SimpleAuthorizationInfo simpleAuthorizationInfo) {
    if(perryAccount.getPrivileges().stream()
        .anyMatch(priv-> priv.equals("Resource Management") || priv.equals("CWS Case Management System"))) {
      simpleAuthorizationInfo.addObjectPermission(new WildcardPermission(PLACEMENT_HOME_READ_PERMISSION));
    }
  }
}
