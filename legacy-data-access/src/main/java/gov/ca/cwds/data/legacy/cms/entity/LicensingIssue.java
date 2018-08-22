package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SystemCode;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Alexander Serbin on 8/21/2018.
 */
@Entity
@Table(name = "LIC_ISST")
@SuppressWarnings({"squid:S3437"}) // LocalDate is serializable
public class LicensingIssue extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  @Basic
  @Column(name = "ISSUE_DT")
  private LocalDate issueDate;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ISSUE_TP")
  private SystemCode issueType;

  @Basic
  @Column(name = "CMPLT_FNDG")
  private String complaintFindingCode;

  @Basic
  @Column(name = "CMPLT_DFCY")
  private String complaintDeficiencyCode;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public LocalDate getIssueDate() {
    return issueDate;
  }

  public void setIssueDate(LocalDate issueDate) {
    this.issueDate = issueDate;
  }

  public SystemCode getIssueType() {
    return issueType;
  }

  public void setIssueType(
    SystemCode issueType) {
    this.issueType = issueType;
  }

  public String getComplaintFindingCode() {
    return complaintFindingCode;
  }

  public void setComplaintFindingCode(String complaintFindingCode) {
    this.complaintFindingCode = complaintFindingCode;
  }

  public String getComplaintDeficiencyCode() {
    return complaintDeficiencyCode;
  }

  public void setComplaintDeficiencyCode(String complaintDeficiencyCode) {
    this.complaintDeficiencyCode = complaintDeficiencyCode;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof LicensingIssue && EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  @Transient
  public Serializable getPrimaryKey() {
    return getIdentifier();
  }

}
