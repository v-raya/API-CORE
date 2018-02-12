package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.entity.enums.CreditReportOutcomeCode;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.CreditAgencyType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.CreditReportRequestedByType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.CreditReportStatusType;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author CWDS TPT-3 Team
 *     <p>Historic information on Credit Report inquiries and the dates associated to them. A Credit
 *     Report History is associated to a Child Client.
 */
@Entity
@Table(name = "CRPTHIST")
@NamedQuery(
  name = CreditReportHistory.FIND_BY_CLIENT_ID,
  query = "FROM CreditReportHistory where childClientId =:" + CreditReportHistory.PARAM_CLIENT_ID
)
public class CreditReportHistory implements PersistentObject {

  public static final String PARAM_CLIENT_ID = "clientId";
  public static final String FIND_BY_CLIENT_ID = "CreditReportHistory.findByClient";

  @Column(name = "THIRD_ID", length = 10)
  private String commentText;

  @Column(name = "CTYREQ_DT")
  private LocalDate countyRequestDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CRDTAGYC", referencedColumnName = "SYS_ID")
  private CreditAgencyType creditAgencyType;

  @Column(name = "OUTCOME_CD", nullable = false, length = 1)
  @ColumnTransformer(read = "trim(OUTCOME_CD)")
  @Convert(converter = CreditReportOutcomeCode.CreditReportOutcomeCodeConverter.class)
  private CreditReportOutcomeCode creditReportOutcomeCode;

  @Column(name = "STATUS_DT")
  private LocalDate creditReportStatusDate;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CRPTSTAC", referencedColumnName = "SYS_ID")
  private CreditReportStatusType creditReportStatusType;

  @Id
  @Column(name = "FKCHLD_CLT")
  private String childClientId;

  @Column(name = "OUTCOME_DT")
  private LocalDate outcomeClearedDate;

  @Column(name = "CRPT_PRVDT")
  private LocalDate reportProvidedDate;

  @NotNull
  @Column(name = "REQRFS_DT")
  private LocalDate requestRefusualDate;

  @NotNull
  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CRPTREQC", referencedColumnName = "SYS_ID")
  private CreditReportRequestedByType requestedByType;

  @Id
  @NotNull
  @Column(name = "THIRD_ID")
  private String thirdId;

  public String getCommentText() {
    return commentText;
  }

  public void setCommentText(String commentText) {
    this.commentText = commentText;
  }

  public LocalDate getCountyRequestDate() {
    return countyRequestDate;
  }

  public void setCountyRequestDate(LocalDate countyRequestDate) {
    this.countyRequestDate = countyRequestDate;
  }

  public CreditAgencyType getCreditAgencyType() {
    return creditAgencyType;
  }

  public void setCreditAgencyType(CreditAgencyType creditAgencyType) {
    this.creditAgencyType = creditAgencyType;
  }

  public CreditReportOutcomeCode getCreditReportOutcomeCode() {
    return creditReportOutcomeCode;
  }

  public void setCreditReportOutcomeCode(CreditReportOutcomeCode creditReportOutcomeCode) {
    this.creditReportOutcomeCode = creditReportOutcomeCode;
  }

  public LocalDate getCreditReportStatusDate() {
    return creditReportStatusDate;
  }

  public void setCreditReportStatusDate(LocalDate creditReportStatusDate) {
    this.creditReportStatusDate = creditReportStatusDate;
  }

  public CreditReportStatusType getCreditReportStatusType() {
    return creditReportStatusType;
  }

  public void setCreditReportStatusType(CreditReportStatusType creditReportStatusType) {
    this.creditReportStatusType = creditReportStatusType;
  }

  public String getChildClientId() {
    return childClientId;
  }

  public void setChildClientId(String childClientId) {
    this.childClientId = childClientId;
  }

  public LocalDate getOutcomeClearedDate() {
    return outcomeClearedDate;
  }

  public void setOutcomeClearedDate(LocalDate outcomeClearedDate) {
    this.outcomeClearedDate = outcomeClearedDate;
  }

  public LocalDate getReportProvidedDate() {
    return reportProvidedDate;
  }

  public void setReportProvidedDate(LocalDate reportProvidedDate) {
    this.reportProvidedDate = reportProvidedDate;
  }

  public LocalDate getRequestRefusualDate() {
    return requestRefusualDate;
  }

  public void setRequestRefusualDate(LocalDate requestRefusualDate) {
    this.requestRefusualDate = requestRefusualDate;
  }

  public CreditReportRequestedByType getRequestedByType() {
    return requestedByType;
  }

  public void setRequestedByType(CreditReportRequestedByType requestedByType) {
    this.requestedByType = requestedByType;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return new CreditReportHistoryPK(thirdId, childClientId);
  }

  public static class CreditReportHistoryPK implements Serializable {

    private static final long serialVersionUID = -1338578310179366105L;

    @Id
    @Column(name = "THIRD_ID", nullable = false, length = 10)
    private String thirdId;

    @Id
    @Column(name = "FKCHLD_CLT", nullable = false, length = 10)
    private String childClientId;

    public CreditReportHistoryPK() {}

    public CreditReportHistoryPK(String thirdId, String childClientId) {
      this.thirdId = thirdId;
      this.childClientId = childClientId;
    }

    public String getThirdId() {
      return thirdId;
    }

    public void setThirdId(String thirdId) {
      this.thirdId = thirdId;
    }

    public String getChildClient() {
      return childClientId;
    }

    public void setChildClient(String childClientId) {
      this.childClientId = childClientId;
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
}
