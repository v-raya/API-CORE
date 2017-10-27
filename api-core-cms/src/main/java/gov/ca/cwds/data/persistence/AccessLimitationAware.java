package gov.ca.cwds.data.persistence;

/**
 * 
 * @author CWDS API Team
 */
@FunctionalInterface
public interface AccessLimitationAware {

  /**
   * Get limited access code or sensitivity indicator
   * 
   * @return Limited access code or sensitivity indicator
   */
  public String getLimitedAccessCode();

}
