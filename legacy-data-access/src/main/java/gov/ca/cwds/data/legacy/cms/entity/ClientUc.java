package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author TPT3 team The converted (uppercase) Common or Other Name of the Client or Archived Client
 *     to be used for Client Search.
 */
@Entity
@Table(name = "CLINT_UC")
@SuppressWarnings({"squid:S3437"}) // LocalDate is serializable
public class ClientUc extends CmsPersistentObject {

  private static final long serialVersionUID = 7176511299767313246L;

  /*
    COMMON_FIRST_NAME - The first name commonly used to refer to a CLIENT. This is not necessarily the CLIENT's legal
    name, it is simply the name by which the CLIENT is referred to by family members, friends, and CWS staff.
  */
  @Column(name = "COM_FST_NM", nullable = false, length = 20)
  private String commonFirstName;

  /*
  COMMON_LAST_NAME - The last name commonly used to refer to a CLIENT.  This is not necessarily the CLIENT's legal
  name, it is simply the name by which the CLIENT is referred to by family members, friends, and CWS staff.
   */
  @Column(name = "COM_LST_NM", nullable = false, length = 25)
  private String commonLastName;

  /*
  COMMON_MIDDLE_NAME - The middle name commonly used to refer to a CLIENT.  This is not necessarily the CLIENT's legal
  name, it is simply the name by which the CLIENT is referred to by family members, friends, and CWS staff.
  */
  @Column(name = "COM_MID_NM", nullable = false, length = 20)
  private String commonMiddleName;

  /*
  SOURCE_TABLE_CODE - This code defines each type of client entity for which a specific uppercase entry was
   defined (e.g. C = Client, N = Other Client Name, A = Archived Other Client Name).
   */
  @Column(name = "SRCTBL_CD", nullable = false, length = 1)
  private String sourceTableCode;

  /*
  SOURCE_TABLE_ID - The logical foreign key representing the ID from the entity which contains the complete client
  name for which the uppercase client name was defined (e.g., the ID from the CLIENT entity). */
  @Id
  @Column(name = "PKTBL_ID", nullable = false, length = 10)
  private String sourceTableId;

  public String getCommonFirstName() {
    return commonFirstName;
  }

  public void setCommonFirstName(String commonFirstName) {
    this.commonFirstName = commonFirstName;
  }

  public String getCommonLastName() {
    return commonLastName;
  }

  public void setCommonLastName(String commonLastName) {
    this.commonLastName = commonLastName;
  }

  public String getCommonMiddleName() {
    return commonMiddleName;
  }

  public void setCommonMiddleName(String commonMiddleName) {
    this.commonMiddleName = commonMiddleName;
  }

  public String getSourceTableCode() {
    return sourceTableCode;
  }

  public void setSourceTableCode(String sourceTableCode) {
    this.sourceTableCode = sourceTableCode;
  }

  public String getSourceTableId() {
    return sourceTableId;
  }

  public void setSourceTableId(String sourceTableId) {
    this.sourceTableId = sourceTableId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    if (!super.equals(o)) {
      return false;
    }

    ClientUc clientUc = (ClientUc) o;
    return isNamesEqual(clientUc)
        && Objects.equals(sourceTableCode, clientUc.sourceTableCode)
        && Objects.equals(sourceTableId, clientUc.sourceTableId);
  }

  private boolean isNamesEqual(ClientUc clientUc) {
    return Objects.equals(commonFirstName, clientUc.commonFirstName)
        && Objects.equals(commonLastName, clientUc.commonLastName)
        && Objects.equals(commonMiddleName, clientUc.commonMiddleName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(
        super.hashCode(),
        commonFirstName,
        commonLastName,
        commonMiddleName,
        sourceTableCode,
        sourceTableId);
  }

  @Override
  public Serializable getPrimaryKey() {
    return getSourceTableId();
  }
}
