package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;

/**
 * {@link CmsPersistentObject} representing a Safely Surrendered Babies.
 * 
 * @author CWDS API Team
 */
@Entity
@Table(name = "SFSUR_BT")
public class SafelySurrenderedBabies extends CmsPersistentObject {

  private static final long serialVersionUID = -7281540421859071478L;

  @Id
  @Column(name = "FKCHLD_CLT", length = CMS_ID_LEN)
  private String childClientId;

  @Column(name = "SURR_DT", nullable = false, length = 10)
  private LocalDate surrenderedDate;

  @Column(name = "SURR_TM", nullable = true, length = 8)
  private LocalTime surrenderedTime;

  @Column(name = "SURR_BY_NM", nullable = false, length = 40)
  private String surrenderedByName;

  @Column(name = "RELTO_CLT", nullable = false, length = 5)
  private Short relationToClient;

  @Column(name = "COMM_DESC", nullable = true, length = 254)
  private String commentDescription;

  @Column(name = "ID_INFO_CD", nullable = false, length = 1)
  private String braceletIdInfoCode;

  @Column(name = "MEDQUES_CD", nullable = false, length = 1)
  private String medicalQuestionnaireCode;

  @Column(name = "QUESREC_DT", nullable = true, length = 10)
  private LocalDate questionnaireReceivedDate;

  @Column(name = "CPSNTF_IND", nullable = false, length = 1)
  @Type(type = "yes_no")
  private Boolean cpsNotofiedIndicator;

  @Column(name = "CPSNTF_DT", nullable = false, length = 10)
  private LocalDate cpsNotofiedDate;

  @Column(name = "CPSNTF_TM", nullable = true, length = 8)
  private LocalTime cpsNotofiedTime;

  @Column(name = "FKSPRJ_CST", nullable = true, length = CMS_ID_LEN)
  private String specialProjectCase;

  @Column(name = "FKSPRJ_RFT", nullable = true, length = CMS_ID_LEN)
  private String specialProjectReferral;

  /**
   * Default no-argument constructor.
   */
  public SafelySurrenderedBabies() {
    super();
  }

  @Override
  public Serializable getPrimaryKey() {
    return getChildClientId();
  }

  public String getChildClientId() {
    return childClientId;
  }

  public void setChildClientId(String childClientId) {
    this.childClientId = childClientId;
  }

  public LocalDate getSurrenderedDate() {
    return surrenderedDate;
  }

  public void setSurrenderedDate(LocalDate surrenderedDate) {
    this.surrenderedDate = surrenderedDate;
  }

  public LocalTime getSurrenderedTime() {
    return surrenderedTime;
  }

  public void setSurrenderedTime(LocalTime surrenderedTime) {
    this.surrenderedTime = surrenderedTime;
  }

  public String getSurrenderedByName() {
    return surrenderedByName;
  }

  public void setSurrenderedByName(String surrenderedByName) {
    this.surrenderedByName = surrenderedByName;
  }

  public Short getRelationToClient() {
    return relationToClient;
  }

  public void setRelationToClient(Short relationToClient) {
    this.relationToClient = relationToClient;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public void setCommentDescription(String commentDescription) {
    this.commentDescription = commentDescription;
  }

  public String getBraceletIdInfoCode() {
    return braceletIdInfoCode;
  }

  public void setBraceletIdInfoCode(String braceletIdInfoCode) {
    this.braceletIdInfoCode = braceletIdInfoCode;
  }

  public String getMedicalQuestionnaireCode() {
    return medicalQuestionnaireCode;
  }

  public void setMedicalQuestionnaireCode(String medicalQuestionnaireCode) {
    this.medicalQuestionnaireCode = medicalQuestionnaireCode;
  }

  public LocalDate getQuestionnaireReceivedDate() {
    return questionnaireReceivedDate;
  }

  public void setQuestionnaireReceivedDate(LocalDate questionnaireReceivedDate) {
    this.questionnaireReceivedDate = questionnaireReceivedDate;
  }

  public Boolean getCpsNotofiedIndicator() {
    return cpsNotofiedIndicator;
  }

  public void setCpsNotofiedIndicator(Boolean cpsNotofiedIndicator) {
    this.cpsNotofiedIndicator = cpsNotofiedIndicator;
  }

  public LocalDate getCpsNotofiedDate() {
    return cpsNotofiedDate;
  }

  public void setCpsNotofiedDate(LocalDate cpsNotofiedDate) {
    this.cpsNotofiedDate = cpsNotofiedDate;
  }

  public LocalTime getCpsNotofiedTime() {
    return cpsNotofiedTime;
  }

  public void setCpsNotofiedTime(LocalTime cpsNotofiedTime) {
    this.cpsNotofiedTime = cpsNotofiedTime;
  }

  public String getSpecialProjectCase() {
    return specialProjectCase;
  }

  public void setSpecialProjectCase(String specialProjectCase) {
    this.specialProjectCase = specialProjectCase;
  }

  public String getSpecialProjectReferral() {
    return specialProjectReferral;
  }

  public void setSpecialProjectReferral(String specialProjectReferral) {
    this.specialProjectReferral = specialProjectReferral;
  }
}
