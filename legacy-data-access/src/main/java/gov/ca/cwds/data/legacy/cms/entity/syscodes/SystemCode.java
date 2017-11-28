package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NamedQuery;

/**
 * @author CWDS TPT-3 Team
 */

@Entity
@Cacheable
@Table(name = "SYS_CD_C")
@NamedQuery(
    name = SystemCode.NQ_FIND_BY_META,
    query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.SystemCode sc WHERE sc.fkMeta = :"
        + SystemCode.NQ_PARAM_META
)
public class SystemCode extends CmsPersistentObject {

  private static final long serialVersionUID = 9161550814720816097L;

  public static final String NQ_FIND_BY_META = "gov.ca.cwds.data.legacy.cms.entity.syscodes.SystemCode.findByFkMeta";
  public static final String NQ_PARAM_META = "fkMeta";

  @Id
  @Column(name = "SYS_ID")
  private Short systemId;

  @Column(name = "CATEGORYID")
  private Short categoryId;

  @Column(name = "INACTV_IND")
  private String inactiveIndicator;

  @Column(name = "OTHER_CD")
  @ColumnTransformer(read = "trim(OTHER_CD)")
  private String otherCode;

  @Column(name = "SHORT_DSC")
  @ColumnTransformer(read = "trim(SHORT_DSC)")
  private String shortDescription;

  @Column(name = "LGC_ID")
  @ColumnTransformer(read = "trim(LGC_ID)")
  private String logicalId;

  @Column(name = "THIRD_ID")
  private String thirdId;

  @Column(name = "LONG_DSC")
  @ColumnTransformer(read = "trim(LONG_DSC)")
  private String longDescription;

  @Column(name = "FKS_META_T", insertable = false, updatable = false)
  private String fkMeta;

  @Override
  public Serializable getPrimaryKey() {
    return getSystemId();
  }

  public Short getSystemId() {
    return systemId;
  }

  public void setSystemId(Short sysId) {
    this.systemId = sysId;
  }

  public Short getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Short categoryid) {
    this.categoryId = categoryid;
  }

  public String getInactiveIndicator() {
    return inactiveIndicator;
  }

  public void setInactiveIndicator(String inactvInd) {
    this.inactiveIndicator = inactvInd;
  }

  public String getOtherCode() {
    return otherCode;
  }

  public void setOtherCode(String otherCd) {
    this.otherCode = otherCd;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDsc) {
    this.shortDescription = shortDsc;
  }

  public String getLogicalId() {
    return logicalId;
  }

  public void setLogicalId(String lgcId) {
    this.logicalId = lgcId;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public String getFkMeta() {
    return fkMeta;
  }

  public void setFkMeta(String fksMetaT) {
    this.fkMeta = fksMetaT;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public void setLongDescription(String longDsc) {
    this.longDescription = longDsc;
  }

}
