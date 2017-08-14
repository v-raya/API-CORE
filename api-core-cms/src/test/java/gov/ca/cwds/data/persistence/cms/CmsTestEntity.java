package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.SystemCodeSerializer;

public class CmsTestEntity extends CmsPersistentObject implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @NotNull
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @NotNull
  @Column(name = "last_name")
  private String lastName;

  @SystemCodeSerializer(logical = true, description = true)
  @Type(type = "short")
  @Column(name = "STATE_ID")
  private Short stateId;

  public CmsTestEntity() {
    super();
  }

  public CmsTestEntity(Long id, String firstName, String lastName, Short stateId) {
    super();

    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.stateId = stateId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return null;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  // @JsonIgnore
  @SystemCodeSerializer(logical = true, description = true)
  public Short getStateId() {
    return stateId;
  }

  public void setStateId(Short stateId) {
    this.stateId = stateId;
  }

}
