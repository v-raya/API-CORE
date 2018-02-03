package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.enums.AllegedFatherPaternityStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.AllegedFatherPaternityStatus.AllegedFatherPaternityStatusConverter;
import gov.ca.cwds.data.legacy.cms.entity.enums.LegalStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.LegalStatus.LegalStatusConverter;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown.YesNoUnknownConverter;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@DiscriminatorColumn(name = "PRMY_IDVCD")
@Table(name = "PTRNYDET")
public abstract class PaternityDetail extends CmsPersistentObject {

  private static final long serialVersionUID = -5009086933409179477L;

  @Id
  @Size(max = CMS_ID_LEN)
  @Column(name = "IDENTIFIER")
  private String id;

  @Column(name = "BFATHER_CD")
  @Convert(converter = YesNoUnknownConverter.class)
  private YesNoUnknown birthFatherStatus;

  @NotNull
  @Size(max = 180)
  @Column(name = "COMNT_DSC")
  @ColumnTransformer(read = "trim(COMNT_DSC)")
  private String commentDescription;

  @Column(name = "ESTB_CD")
  @Convert(converter = AllegedFatherPaternityStatusConverter.class)
  private AllegedFatherPaternityStatus allegedFatherEstablishedPaternity;

  @NotNull
  @Size(max = 50)
  @Column(name = "ETBLOC_DSC")
  @ColumnTransformer(read = "trim(ETBLOC_DSC)")
  private String establishedLocationDescription;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "FKCHLD_CLT",
      referencedColumnName = "FKCLIENT_T",
      insertable = false,
      updatable = false
  )
  private ChildClient childClient;

  @Column(name = "HEARING_DT")
  private LocalDate paternityHearingDate;

  @Column(name = "LGLDSG_CD")
  @Convert(converter = LegalStatusConverter.class)
  private LegalStatus clientLegalStatus;

  @Column(name = "TEST_DT", nullable = true)
  private LocalDate paternityTestDate;

  @NotNull
  @Size(max = 60)
  @Column(name = "TSTRSL_DSC")
  @ColumnTransformer(read = "trim(TSTRSL_DSC)")
  private String paternityTestResultsDescription;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public AllegedFatherPaternityStatus getAllegedFatherEstablishedPaternity() {
    return allegedFatherEstablishedPaternity;
  }

  public void setAllegedFatherEstablishedPaternity(
      AllegedFatherPaternityStatus allegedFatherEstablishedPaternity) {
    this.allegedFatherEstablishedPaternity = allegedFatherEstablishedPaternity;
  }

  public LocalDate getPaternityHearingDate() {
    return paternityHearingDate;
  }

  public void setPaternityHearingDate(LocalDate paternityHearingDate) {
    this.paternityHearingDate = paternityHearingDate;
  }

  public LocalDate getPaternityTestDate() {
    return paternityTestDate;
  }

  public void setPaternityTestDate(LocalDate paternityTestDate) {
    this.paternityTestDate = paternityTestDate;
  }

  public String getCommentDescription() {
    return commentDescription;
  }

  public void setCommentDescription(String comntDsc) {
    this.commentDescription = comntDsc;
  }

  public String getEstablishedLocationDescription() {
    return establishedLocationDescription;
  }

  public void setEstablishedLocationDescription(String etblocDsc) {
    this.establishedLocationDescription = etblocDsc;
  }

  public String getPaternityTestResultsDescription() {
    return paternityTestResultsDescription;
  }

  public void setPaternityTestResultsDescription(String tstrslDsc) {
    this.paternityTestResultsDescription = tstrslDsc;
  }

  public ChildClient getChildClient() {
    return childClient;
  }

  public void setChildClient(ChildClient childClient) {
    this.childClient = childClient;
  }

  public LegalStatus getClientLegalStatus() {
    return clientLegalStatus;
  }

  public void setClientLegalStatus(LegalStatus clientLegalStatus) {
    this.clientLegalStatus = clientLegalStatus;
  }

  public YesNoUnknown getBirthFatherStatus() {
    return birthFatherStatus;
  }

  public void setBirthFatherStatus(YesNoUnknown birthFatherStatus) {
    this.birthFatherStatus = birthFatherStatus;
  }
}
