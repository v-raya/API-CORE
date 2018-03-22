package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/** @author CWDS TPT-3 Team */
@NamedQuery(
  name = OtherClientName.NQ_FIND_BY_CLIENT_ID,
  query =
      "SELECT ocn FROM gov.ca.cwds.data.legacy.cms.entity.OtherClientName ocn"
          + " WHERE ocn.clientId = :"
          + OtherClientName.NQ_PARAM_CLIENT_ID
)
@Entity
@Table(name = "OCL_NM_T")
public class OtherClientName extends CmsPersistentObject {
  public static final String NQ_FIND_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.OtherClientName.findByClientId";
  public static final String NQ_PARAM_CLIENT_ID = "clientId";

  private static final long serialVersionUID = 884109933245580253L;

  /*
    FIRST_NAME - The first, given name or initial of The CLIENT.
  */
  @Column(name = "FIRST_NM", nullable = false, length = 20)
  private String firstName;

  /*
   FKCLIENT_T - Mandatory Foreign key that NAMES a CLIENT.
  */
  @Column(name = "FKCLIENT_T", nullable = false, length = CMS_ID_LEN)
  private String clientId;

  /*
   LAST_NAME - The last or surname of the CLIENT.
  */
  @Column(name = "LAST_NM", length = 25, nullable = false)
  private String lastName;

  /*
   MIDDLE_NAME - The middle name or initial of the CLIENT.
  */
  @Column(name = "MIDDLE_NM", length = 20, nullable = false)
  private String middleName;

  /*
    NAME_PREFIX_DESCRIPTION - The salutation form to be used in the mailing
  address of a CLIENT (e.g., Mr., Ms., Mrs., Dr., Miss, Rev., etc.).
     */
  @Column(name = "NMPRFX_DSC", length = 6, nullable = false)
  private String namePrefixDescription;

  /*
    NAME_TYPE - The system number which identifies the type of NAME
  for a CLIENT (e.g., legal, AKA, etc.).
   */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "NAME_TPC", referencedColumnName = "SYS_ID")
  private NameType nameType;

  /*
   SUFFIX_TITLE_DESCRIPTION - The suffix name of a CLIENT (e.g., Esq., M.D., Ph.D., D.D.S., etc.).
  */
  @Column(name = "SUFX_TLDSC", length = 4, nullable = false)
  private String suffixTitleDescription;

  /*
   THIRD_ID - This is a system generated unique number that supplements user supplied data in the primary key.
  */
  @Id
  @Column(name = "THIRD_ID", length = CMS_ID_LEN, nullable = false)
  private String thirdId;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getNamePrefixDescription() {
    return namePrefixDescription;
  }

  public void setNamePrefixDescription(String namePrefixDescription) {
    this.namePrefixDescription = namePrefixDescription;
  }

  public NameType getNameType() {
    return nameType;
  }

  public void setNameType(NameType nameType) {
    this.nameType = nameType;
  }

  public String getSuffixTitleDescription() {
    return suffixTitleDescription;
  }

  public void setSuffixTitleDescription(String suffixTitleDescription) {
    this.suffixTitleDescription = suffixTitleDescription;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return thirdId;
  }
}
