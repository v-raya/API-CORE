package gov.ca.cwds.rest.api;

import java.io.Serializable;
import java.util.Set;

/**
 * {@link Response} wrapping a primary key.
 * 
 * @author CWDS API Team
 */
public class PrimaryKeyResponse implements Response {

  private Serializable primaryKey;

  public PrimaryKeyResponse(Serializable primaryKey) {
    super();
    this.primaryKey = primaryKey;
  }

  /**
   * @return the primaryKey
   */
  public Serializable getPrimaryKey() {
    return primaryKey;
  }

  @Override
  public boolean hasMessages() { return false; }

  @Override
  public Set getMessages() { return null; }
}
