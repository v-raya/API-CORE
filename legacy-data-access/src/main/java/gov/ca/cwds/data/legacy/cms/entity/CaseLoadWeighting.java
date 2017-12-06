package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Entity representing CASE_LOAD_WEIGHTING from legacy system.
 * This entity type contains the current accumulated weighting of the set of ASSIGNMENTs in
 * a CASE LOAD that is associated with a particular STAFF PERSON. Occurrences of this entity type
 * will only exist for active CASE LOADs. This is basically denormalized data to shorten access paths.
 *
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "CSLDWGHT")
public class CaseLoadWeighting extends CmsPersistentObject {

  private static final long serialVersionUID = 6535755418955607474L;

  @Column(name = "FIRST_NM", nullable = false, length = 20)
  @ColumnTransformer(read = "trim(FIRST_NM)")
  private String firstName;

  @Column(name = "LAST_NM", nullable = false, length = 25)
  @ColumnTransformer(read = "trim(LAST_NM)")
  private String lastName;

  @Column(name = "MID_INI_NM", nullable = false, length = 1)
  private String middleInitialName;

  @Column(name = "NMPRFX_DSC", nullable = false, length = 6)
  @ColumnTransformer(read = "trim(NMPRFX_DSC)")
  private String namePrefix;

  @Column(name = "SUFX_TLDSC", nullable = false, length = 4)
  @ColumnTransformer(read = "trim(SUFX_TLDSC)")
  private String suffixTitle;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CNTY_SPFCD", referencedColumnName = "SYS_ID", insertable = false, updatable = false)
  private County county;

  @Id
  @Column(name = "FKCASE_LDT", nullable = false, length = 10)
  private String fkcaseLdt;

  @Column(name = "FKSTFPERST", nullable = false, length = 3)
  private String fkstfperst;

  @Override
  public Serializable getPrimaryKey() {
    return fkcaseLdt;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstNm) {
    this.firstName = firstNm;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastNm) {
    this.lastName = lastNm;
  }

  public String getMiddleInitialName() {
    return middleInitialName;
  }

  public void setMiddleInitialName(String midIniNm) {
    this.middleInitialName = midIniNm;
  }

  public String getNamePrefix() {
    return namePrefix;
  }

  public void setNamePrefix(String nmprfxDsc) {
    this.namePrefix = nmprfxDsc;
  }

  public String getSuffixTitle() {
    return suffixTitle;
  }

  public void setSuffixTitle(String sufxTldsc) {
    this.suffixTitle = sufxTldsc;
  }

  public String getFkcaseLdt() {
    return fkcaseLdt;
  }

  public void setFkcaseLdt(String fkcaseLdt) {
    this.fkcaseLdt = fkcaseLdt;
  }

  public String getFkstfperst() {
    return fkstfperst;
  }

  public void setFkstfperst(String fkstfperst) {
    this.fkstfperst = fkstfperst;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof CaseLoadWeighting)) {
      return false;
    }

    CaseLoadWeighting that = (CaseLoadWeighting) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(fkcaseLdt, that.fkcaseLdt)
        .append(firstName, that.firstName)
        .append(lastName, that.lastName)
        .append(middleInitialName, that.middleInitialName)
        .append(namePrefix, that.namePrefix)
        .append(suffixTitle, that.suffixTitle)
        .append(fkstfperst, that.fkstfperst)
        .append(county, that.county)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(fkcaseLdt)
        .append(firstName)
        .append(lastName)
        .append(middleInitialName)
        .append(namePrefix)
        .append(suffixTitle)
        .append(fkstfperst)
        .append(county)
        .toHashCode();
  }
}
