package gov.ca.cwds.data;

import java.io.Serializable;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiObjectIdentity;

public final class TestPersistentObject extends ApiObjectIdentity implements PersistentObject {

  private String id;

  public TestPersistentObject() {

  }

  public TestPersistentObject(String id) {
    this.id = id;
  }

  @Override
  public Serializable getPrimaryKey() {
    return id;
  }

  String getId() {
    return id;
  }

  void setId(String id) {
    this.id = id;
  }

}
