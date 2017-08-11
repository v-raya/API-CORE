package gov.ca.cwds.rest.api.domain;

import java.io.Serializable;

/**
 * Base domain class for all API domain objects.
 * 
 * @author CWDS API Team
 */
public abstract class DomainObject implements Serializable {

  /**
   * Base serialization version. Increment by class change.
   */
  private static final long serialVersionUID = 1L;
  public static final String DATE_FORMAT = DomainChef.DATE_FORMAT;
  public static final String TIME_FORMAT = DomainChef.TIME_FORMAT;

  /**
   * 
   */
  public DomainObject() {}

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();
}
