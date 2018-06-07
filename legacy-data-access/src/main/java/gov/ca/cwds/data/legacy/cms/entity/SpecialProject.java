package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;

/**
 * Special projects CMS entity.
 *
 * @author CWDS TPT Team
 */
@Entity
@Table(name = "SPC_PRJT")
@NamedQuery(name = SpecialProject.FIND_ACTIVE_SSB_BY_GOVERNMENT_ENTITY,
    query = "FROM SpecialProject WHERE PROJECT_NM = " + SpecialProject.SSB_SPECIAL_PROJECT_NAME
        + " AND END_DT IS NULL AND GVR_ENTC = :governmentEntity")

public class SpecialProject extends CmsPersistentObject {

  private static final long serialVersionUID = 241170224860954003L;

  public static final String FIND_ACTIVE_SSB_BY_GOVERNMENT_ENTITY =
      "SpecialProject.findActiveSSBByGovernmentEntity";
  public static final String SSB_SPECIAL_PROJECT_NAME = "S-Safely Surrendered Baby";
  public static final String PARAM_GOVERNMENT_ENTITY = "governmentEntity";

  @Id
  @Column(name = "IDENTIFIER", length = CMS_ID_LEN)
  private String id;

  @Column(name = "PROJECT_NM")
  @ColumnTransformer(read = "trim(PROJECT_NM)")
  private String name;

  @Column(name = "PRJCT_DSC")
  @ColumnTransformer(read = "trim(PRJCT_DSC)")
  private String description;

  @Column(name = "GVR_ENTC")
  private Short governmentEntity;

  @Column(name = "START_DT")
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Column(name = "ARCASS_IND")
  @Type(type = "yes_no")
  private Boolean arcassIndicator;

  /**
   * No-argument constructor
   */
  public SpecialProject() {
    // No-argument constructor
  }

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Short getGovernmentEntity() {
    return governmentEntity;
  }

  public void setGovernmentEntity(Short governmentEntity) {
    this.governmentEntity = governmentEntity;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Boolean getArcassIndicator() {
    return arcassIndicator;
  }

  public void setArcassIndicator(Boolean arcassIndicator) {
    this.arcassIndicator = arcassIndicator;
  }
}
