package gov.ca.cwds.auth;

import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.shiro.subject.Subject;

public class User {
  private final String racf;
  private final Subject subject;

  public User(String racf, Subject subject) {
    this.racf = checkNotNull(racf, "RACF ID cannot be null.");
    this.subject = checkNotNull(subject, "Subject cannot be null.");
  }

  /**
   * @return the racf
   */
  public String getRacf() {
    return racf;
  }

  /**
   * @return the subject
   */
  public Subject getSubject() {
    return subject;
  }
}
