package gov.ca.cwds.rest.api.domain;

public abstract class DomainObject {
  public static final String DATE_FORMAT = DomainChef.DATE_FORMAT;
  public static final String TIME_FORMAT = DomainChef.TIME_FORMAT;

  public DomainObject() {}

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();
}
