package gov.ca.cwds.rest.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import gov.ca.cwds.rest.api.domain.error.ErrorMessage;

/**
 * {@link Response} wrapping a primary key.
 * 
 * @author CWDS API Team
 */
public class PrimaryKeyResponse implements Response {

  private static final long serialVersionUID = 1L;

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
  public boolean hasMessages() {
    return false;
  }

  @Override
  public List<ErrorMessage> getMessages() {
    return new ArrayList<>();
  }

}
