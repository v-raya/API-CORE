package gov.ca.cwds.cms.data.access.utils;

import gov.ca.cwds.security.realm.PerryAccount;
import java.util.Optional;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class PerryUtils {

  private PerryUtils(){}

  public static PerryAccount getPerryAccount() {
    Optional<PerryAccount> perryAccount = Optional.empty();
    Subject currentUser = SecurityUtils.getSubject();
    if (currentUser.getPrincipals() != null) {
      perryAccount = currentUser.getPrincipals().asList().stream()
          .filter(principal -> principal instanceof PerryAccount).findAny();
    }
    return perryAccount.orElseThrow(IllegalStateException::new);
  }
}
