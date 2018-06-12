package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;

/**
 * Non-CWS numbers.
 *
 * @author CWDS TPT Team
 */
@Entity
@Table(name = "NCWSNO_T")
@IdClass(NonCWSNumber.PrimaryKey.class)
public class NonCWSNumber extends CmsPersistentObject {

  private static final long serialVersionUID = -7079045042245993656L;

  /**
   * Primary key for NCWSNO_T.
   *
   */
  public static class PrimaryKey implements Serializable {

    private static final long serialVersionUID = 4255321117205377921L;

    @Id
    @Column(name = "THIRD_ID", nullable = false, length = CMS_ID_LEN)
    private String thirdId;

    @Id
    @Column(name = "FKCLIENT_T", nullable = false, length = CMS_ID_LEN)
    private String clientId;

    /**
     * Default no-argument constructor.
     */
    public PrimaryKey() {
      super();
    }

    /**
     * Construct with parameters.
     * 
     * @param thirdId The third ID.
     * @param clientId The client ID.
     */
    public PrimaryKey(String thirdId, String clientId) {
      this.thirdId = thirdId;
      this.clientId = clientId;
    }

    public String getThirdId() {
      return thirdId;
    }

    public void setThirdId(String thirdId) {
      this.thirdId = thirdId;
    }

    public String getClientId() {
      return clientId;
    }

    public void setClientId(String clientId) {
      this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
    }
  }

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = CMS_ID_LEN)
  private String thirdId;

  @Id
  @Column(name = "FKCLIENT_T", nullable = false, length = CMS_ID_LEN)
  private String clientId;

  @Column(name = "OTH_ID_C")
  private Short otherIdCode;

  @Column(name = "OTHER_CLID")
  private String otherId;

  /**
   * No-argument constructor.
   */
  public NonCWSNumber() {
    super();
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public Short getOtherIdCode() {
    return otherIdCode;
  }

  public void setOtherIdCode(Short otherIdCode) {
    this.otherIdCode = otherIdCode;
  }

  public String getOtherId() {
    return otherId;
  }

  public void setOtherId(String otherId) {
    this.otherId = otherId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return new PrimaryKey(getThirdId(), getClientId());
  }
}
