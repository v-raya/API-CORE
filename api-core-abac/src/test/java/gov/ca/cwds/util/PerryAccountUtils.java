package gov.ca.cwds.util;

import gov.ca.cwds.security.realm.PerryAccount;
import java.util.Arrays;
import java.util.HashSet;

/**
 * @author CWDS TPT-3 Team
 */
public class PerryAccountUtils {

  public static PerryAccount initPerryAccountWithPrivileges(String... privileges) {
    final PerryAccount perryAccount = new PerryAccount();
    final HashSet<String> privilegeSet = new HashSet<>();
    privilegeSet.addAll(Arrays.asList(privileges));
    perryAccount.setPrivileges(privilegeSet);
    return perryAccount;
  }

}
