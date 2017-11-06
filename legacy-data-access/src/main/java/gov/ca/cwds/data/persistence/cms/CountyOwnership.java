package gov.ca.cwds.data.persistence.cms;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 *
 * This entity will contain folded key rows for each row originating from a super parent entity. It
 * will be used to track which counties should have access to a particular row of data.
 */
@Entity
@Table(name = "CNTYOWNT")
@IdClass(CountyOwnershipPK.class)
@SuppressWarnings({"squid:S3437"}) //LocalDate is serializable
public class CountyOwnership implements PersistentObject {

  private static final long serialVersionUID = 8306097504998852336L;

  /**
   * ENTITY_IDENTIFIER - The identifier of the parent entity row to be owned by 1 or more counties.
   */
  @Id
  @Column(name = "ENTITY_ID", nullable = false, length = 10)
  private String entityId;

  /**
   * ENTITY_CODE - A folded key code indicating which parent entity entity_identifier is for (e.g.
   * A= Address, C= Client, E= Education Provider, F= Foreign Address, G= Group Home Org., I=
   * Collateral Individual, P= Placement Home, S= Substitute Care Provider, T= Attorney, V= Service
   * Provider.)
   */
  @Id
  @Column(name = "ENTITY_CD", nullable = false, length = 2)
  private String entityCd;

  /**
   * MULTIPLE_COUNTIES_FLAG - This flag indicates whether multiple counties have access to a
   * particular row.
   */
  @Basic
  @Column(name = "MULTI_FLG", nullable = false, length = 1)
  private String multiFlg;

  /**
   * COUNTY_00_FLAG - A flag used to determine if the county whose number matches the number, or the
   * default 99,  of the attribute has authority to view a particular row.
   */
  @Basic
  @Column(name = "CTY_00_FLG", nullable = false, length = 1)
  private String cty00Flg;

  @Basic
  @Column(name = "CTY_01_FLG", nullable = false, length = 1)
  private String cty01Flg;

  @Basic
  @Column(name = "CTY_02_FLG", nullable = false, length = 1)
  private String cty02Flg;

  @Basic
  @Column(name = "CTY_03_FLG", nullable = false, length = 1)
  private String cty03Flg;

  @Basic
  @Column(name = "CTY_04_FLG", nullable = false, length = 1)
  private String cty04Flg;

  @Basic
  @Column(name = "CTY_05_FLG", nullable = false, length = 1)
  private String cty05Flg;

  @Basic
  @Column(name = "CTY_06_FLG", nullable = false, length = 1)
  private String cty06Flg;

  @Basic
  @Column(name = "CTY_07_FLG", nullable = false, length = 1)
  private String cty07Flg;

  @Basic
  @Column(name = "CTY_08_FLG", nullable = false, length = 1)
  private String cty08Flg;

  @Basic
  @Column(name = "CTY_09_FLG", nullable = false, length = 1)
  private String cty09Flg;

  @Basic
  @Column(name = "CTY_10_FLG", nullable = false, length = 1)
  private String cty10Flg;

  @Basic
  @Column(name = "CTY_11_FLG", nullable = false, length = 1)
  private String cty11Flg;

  @Basic
  @Column(name = "CTY_12_FLG", nullable = false, length = 1)
  private String cty12Flg;

  @Basic
  @Column(name = "CTY_13_FLG", nullable = false, length = 1)
  private String cty13Flg;

  @Basic
  @Column(name = "CTY_14_FLG", nullable = false, length = 1)
  private String cty14Flg;

  @Basic
  @Column(name = "CTY_15_FLG", nullable = false, length = 1)
  private String cty15Flg;

  @Basic
  @Column(name = "CTY_16_FLG", nullable = false, length = 1)
  private String cty16Flg;

  @Basic
  @Column(name = "CTY_17_FLG", nullable = false, length = 1)
  private String cty17Flg;

  @Basic
  @Column(name = "CTY_18_FLG", nullable = false, length = 1)
  private String cty18Flg;

  @Basic
  @Column(name = "CTY_19_FLG", nullable = false, length = 1)
  private String cty19Flg;

  @Basic
  @Column(name = "CTY_20_FLG", nullable = false, length = 1)
  private String cty20Flg;

  @Basic
  @Column(name = "CTY_21_FLG", nullable = false, length = 1)
  private String cty21Flg;

  @Basic
  @Column(name = "CTY_22_FLG", nullable = false, length = 1)
  private String cty22Flg;

  @Basic
  @Column(name = "CTY_23_FLG", nullable = false, length = 1)
  private String cty23Flg;

  @Basic
  @Column(name = "CTY_24_FLG", nullable = false, length = 1)
  private String cty24Flg;

  @Basic
  @Column(name = "CTY_25_FLG", nullable = false, length = 1)
  private String cty25Flg;

  @Basic
  @Column(name = "CTY_26_FLG", nullable = false, length = 1)
  private String cty26Flg;

  @Basic
  @Column(name = "CTY_27_FLG", nullable = false, length = 1)
  private String cty27Flg;

  @Basic
  @Column(name = "CTY_28_FLG", nullable = false, length = 1)
  private String cty28Flg;

  @Basic
  @Column(name = "CTY_29_FLG", nullable = false, length = 1)
  private String cty29Flg;

  @Basic
  @Column(name = "CTY_30_FLG", nullable = false, length = 1)
  private String cty30Flg;

  @Basic
  @Column(name = "CTY_31_FLG", nullable = false, length = 1)
  private String cty31Flg;

  @Basic
  @Column(name = "CTY_32_FLG", nullable = false, length = 1)
  private String cty32Flg;

  @Basic
  @Column(name = "CTY_33_FLG", nullable = false, length = 1)
  private String cty33Flg;

  @Basic
  @Column(name = "CTY_34_FLG", nullable = false, length = 1)
  private String cty34Flg;

  @Basic
  @Column(name = "CTY_35_FLG", nullable = false, length = 1)
  private String cty35Flg;

  @Basic
  @Column(name = "CTY_36_FLG", nullable = false, length = 1)
  private String cty36Flg;

  @Basic
  @Column(name = "CTY_37_FLG", nullable = false, length = 1)
  private String cty37Flg;

  @Basic
  @Column(name = "CTY_38_FLG", nullable = false, length = 1)
  private String cty38Flg;

  @Basic
  @Column(name = "CTY_39_FLG", nullable = false, length = 1)
  private String cty39Flg;

  @Basic
  @Column(name = "CTY_40_FLG", nullable = false, length = 1)
  private String cty40Flg;

  @Basic
  @Column(name = "CTY_41_FLG", nullable = false, length = 1)
  private String cty41Flg;

  @Basic
  @Column(name = "CTY_42_FLG", nullable = false, length = 1)
  private String cty42Flg;

  @Basic
  @Column(name = "CTY_43_FLG", nullable = false, length = 1)
  private String cty43Flg;

  @Basic
  @Column(name = "CTY_44_FLG", nullable = false, length = 1)
  private String cty44Flg;

  @Basic
  @Column(name = "CTY_45_FLG", nullable = false, length = 1)
  private String cty45Flg;

  @Basic
  @Column(name = "CTY_46_FLG", nullable = false, length = 1)
  private String cty46Flg;

  @Basic
  @Column(name = "CTY_47_FLG", nullable = false, length = 1)
  private String cty47Flg;

  @Basic
  @Column(name = "CTY_48_FLG", nullable = false, length = 1)
  private String cty48Flg;

  @Basic
  @Column(name = "CTY_49_FLG", nullable = false, length = 1)
  private String cty49Flg;

  @Basic
  @Column(name = "CTY_50_FLG", nullable = false, length = 1)
  private String cty50Flg;

  @Basic
  @Column(name = "CTY_51_FLG", nullable = false, length = 1)
  private String cty51Flg;

  @Basic
  @Column(name = "CTY_52_FLG", nullable = false, length = 1)
  private String cty52Flg;

  @Basic
  @Column(name = "CTY_53_FLG", nullable = false, length = 1)
  private String cty53Flg;

  @Basic
  @Column(name = "CTY_54_FLG", nullable = false, length = 1)
  private String cty54Flg;

  @Basic
  @Column(name = "CTY_55_FLG", nullable = false, length = 1)
  private String cty55Flg;

  @Basic
  @Column(name = "CTY_56_FLG", nullable = false, length = 1)
  private String cty56Flg;

  @Basic
  @Column(name = "CTY_57_FLG", nullable = false, length = 1)
  private String cty57Flg;

  @Basic
  @Column(name = "CTY_58_FLG", nullable = false, length = 1)
  private String cty58Flg;

  @Basic
  @Column(name = "CTY_59_FLG", nullable = false, length = 1)
  private String cty59Flg;

  @Basic
  @Column(name = "CTY_60_FLG", nullable = false, length = 1)
  private String cty60Flg;

  @Basic
  @Column(name = "CTY_61_FLG", nullable = false, length = 1)
  private String cty61Flg;

  @Basic
  @Column(name = "CTY_62_FLG", nullable = false, length = 1)
  private String cty62Flg;

  @Basic
  @Column(name = "CTY_63_FLG", nullable = false, length = 1)
  private String cty63Flg;

  /**
   * DELETE_DATE - This indicates the date the associated parent entity was deleted.
   */
  @Basic
  @Column(name = "DELETE_DT", nullable = true)
  private LocalDate deleteDt;

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getEntityCd() {
    return entityCd;
  }

  public void setEntityCd(String entityCd) {
    this.entityCd = entityCd;
  }

  public String getMultiFlg() {
    return multiFlg;
  }

  public void setMultiFlg(String multiFlg) {
    this.multiFlg = multiFlg;
  }

  public String getCty00Flg() {
    return cty00Flg;
  }

  public void setCty00Flg(String cty00Flg) {
    this.cty00Flg = cty00Flg;
  }

  public String getCty01Flg() {
    return cty01Flg;
  }

  public void setCty01Flg(String cty01Flg) {
    this.cty01Flg = cty01Flg;
  }

  public String getCty02Flg() {
    return cty02Flg;
  }

  public void setCty02Flg(String cty02Flg) {
    this.cty02Flg = cty02Flg;
  }

  public String getCty03Flg() {
    return cty03Flg;
  }

  public void setCty03Flg(String cty03Flg) {
    this.cty03Flg = cty03Flg;
  }

  public String getCty04Flg() {
    return cty04Flg;
  }

  public void setCty04Flg(String cty04Flg) {
    this.cty04Flg = cty04Flg;
  }

  public String getCty05Flg() {
    return cty05Flg;
  }

  public void setCty05Flg(String cty05Flg) {
    this.cty05Flg = cty05Flg;
  }

  public String getCty06Flg() {
    return cty06Flg;
  }

  public void setCty06Flg(String cty06Flg) {
    this.cty06Flg = cty06Flg;
  }

  public String getCty07Flg() {
    return cty07Flg;
  }

  public void setCty07Flg(String cty07Flg) {
    this.cty07Flg = cty07Flg;
  }

  public String getCty08Flg() {
    return cty08Flg;
  }

  public void setCty08Flg(String cty08Flg) {
    this.cty08Flg = cty08Flg;
  }

  public String getCty09Flg() {
    return cty09Flg;
  }

  public void setCty09Flg(String cty09Flg) {
    this.cty09Flg = cty09Flg;
  }

  public String getCty10Flg() {
    return cty10Flg;
  }

  public void setCty10Flg(String cty10Flg) {
    this.cty10Flg = cty10Flg;
  }

  public String getCty11Flg() {
    return cty11Flg;
  }

  public void setCty11Flg(String cty11Flg) {
    this.cty11Flg = cty11Flg;
  }

  public String getCty12Flg() {
    return cty12Flg;
  }

  public void setCty12Flg(String cty12Flg) {
    this.cty12Flg = cty12Flg;
  }

  public String getCty13Flg() {
    return cty13Flg;
  }

  public void setCty13Flg(String cty13Flg) {
    this.cty13Flg = cty13Flg;
  }

  public String getCty14Flg() {
    return cty14Flg;
  }

  public void setCty14Flg(String cty14Flg) {
    this.cty14Flg = cty14Flg;
  }

  public String getCty15Flg() {
    return cty15Flg;
  }

  public void setCty15Flg(String cty15Flg) {
    this.cty15Flg = cty15Flg;
  }

  public String getCty16Flg() {
    return cty16Flg;
  }

  public void setCty16Flg(String cty16Flg) {
    this.cty16Flg = cty16Flg;
  }

  public String getCty17Flg() {
    return cty17Flg;
  }

  public void setCty17Flg(String cty17Flg) {
    this.cty17Flg = cty17Flg;
  }

  public String getCty18Flg() {
    return cty18Flg;
  }

  public void setCty18Flg(String cty18Flg) {
    this.cty18Flg = cty18Flg;
  }

  public String getCty19Flg() {
    return cty19Flg;
  }

  public void setCty19Flg(String cty19Flg) {
    this.cty19Flg = cty19Flg;
  }

  public String getCty20Flg() {
    return cty20Flg;
  }

  public void setCty20Flg(String cty20Flg) {
    this.cty20Flg = cty20Flg;
  }

  public String getCty21Flg() {
    return cty21Flg;
  }

  public void setCty21Flg(String cty21Flg) {
    this.cty21Flg = cty21Flg;
  }

  public String getCty22Flg() {
    return cty22Flg;
  }

  public void setCty22Flg(String cty22Flg) {
    this.cty22Flg = cty22Flg;
  }

  public String getCty23Flg() {
    return cty23Flg;
  }

  public void setCty23Flg(String cty23Flg) {
    this.cty23Flg = cty23Flg;
  }

  public String getCty24Flg() {
    return cty24Flg;
  }

  public void setCty24Flg(String cty24Flg) {
    this.cty24Flg = cty24Flg;
  }

  public String getCty25Flg() {
    return cty25Flg;
  }

  public void setCty25Flg(String cty25Flg) {
    this.cty25Flg = cty25Flg;
  }

  public String getCty26Flg() {
    return cty26Flg;
  }

  public void setCty26Flg(String cty26Flg) {
    this.cty26Flg = cty26Flg;
  }

  public String getCty27Flg() {
    return cty27Flg;
  }

  public void setCty27Flg(String cty27Flg) {
    this.cty27Flg = cty27Flg;
  }

  public String getCty28Flg() {
    return cty28Flg;
  }

  public void setCty28Flg(String cty28Flg) {
    this.cty28Flg = cty28Flg;
  }

  public String getCty29Flg() {
    return cty29Flg;
  }

  public void setCty29Flg(String cty29Flg) {
    this.cty29Flg = cty29Flg;
  }

  public String getCty30Flg() {
    return cty30Flg;
  }

  public void setCty30Flg(String cty30Flg) {
    this.cty30Flg = cty30Flg;
  }

  public String getCty31Flg() {
    return cty31Flg;
  }

  public void setCty31Flg(String cty31Flg) {
    this.cty31Flg = cty31Flg;
  }

  public String getCty32Flg() {
    return cty32Flg;
  }

  public void setCty32Flg(String cty32Flg) {
    this.cty32Flg = cty32Flg;
  }

  public String getCty33Flg() {
    return cty33Flg;
  }

  public void setCty33Flg(String cty33Flg) {
    this.cty33Flg = cty33Flg;
  }

  public String getCty34Flg() {
    return cty34Flg;
  }

  public void setCty34Flg(String cty34Flg) {
    this.cty34Flg = cty34Flg;
  }

  public String getCty35Flg() {
    return cty35Flg;
  }

  public void setCty35Flg(String cty35Flg) {
    this.cty35Flg = cty35Flg;
  }

  public String getCty36Flg() {
    return cty36Flg;
  }

  public void setCty36Flg(String cty36Flg) {
    this.cty36Flg = cty36Flg;
  }

  public String getCty37Flg() {
    return cty37Flg;
  }

  public void setCty37Flg(String cty37Flg) {
    this.cty37Flg = cty37Flg;
  }

  public String getCty38Flg() {
    return cty38Flg;
  }

  public void setCty38Flg(String cty38Flg) {
    this.cty38Flg = cty38Flg;
  }

  public String getCty39Flg() {
    return cty39Flg;
  }

  public void setCty39Flg(String cty39Flg) {
    this.cty39Flg = cty39Flg;
  }

  public String getCty40Flg() {
    return cty40Flg;
  }

  public void setCty40Flg(String cty40Flg) {
    this.cty40Flg = cty40Flg;
  }

  public String getCty41Flg() {
    return cty41Flg;
  }

  public void setCty41Flg(String cty41Flg) {
    this.cty41Flg = cty41Flg;
  }

  public String getCty42Flg() {
    return cty42Flg;
  }

  public void setCty42Flg(String cty42Flg) {
    this.cty42Flg = cty42Flg;
  }

  public String getCty43Flg() {
    return cty43Flg;
  }

  public void setCty43Flg(String cty43Flg) {
    this.cty43Flg = cty43Flg;
  }

  public String getCty44Flg() {
    return cty44Flg;
  }

  public void setCty44Flg(String cty44Flg) {
    this.cty44Flg = cty44Flg;
  }

  public String getCty45Flg() {
    return cty45Flg;
  }

  public void setCty45Flg(String cty45Flg) {
    this.cty45Flg = cty45Flg;
  }

  public String getCty46Flg() {
    return cty46Flg;
  }

  public void setCty46Flg(String cty46Flg) {
    this.cty46Flg = cty46Flg;
  }

  public String getCty47Flg() {
    return cty47Flg;
  }

  public void setCty47Flg(String cty47Flg) {
    this.cty47Flg = cty47Flg;
  }

  public String getCty48Flg() {
    return cty48Flg;
  }

  public void setCty48Flg(String cty48Flg) {
    this.cty48Flg = cty48Flg;
  }

  public String getCty49Flg() {
    return cty49Flg;
  }

  public void setCty49Flg(String cty49Flg) {
    this.cty49Flg = cty49Flg;
  }

  public String getCty50Flg() {
    return cty50Flg;
  }

  public void setCty50Flg(String cty50Flg) {
    this.cty50Flg = cty50Flg;
  }

  public String getCty51Flg() {
    return cty51Flg;
  }

  public void setCty51Flg(String cty51Flg) {
    this.cty51Flg = cty51Flg;
  }

  public String getCty52Flg() {
    return cty52Flg;
  }

  public void setCty52Flg(String cty52Flg) {
    this.cty52Flg = cty52Flg;
  }

  public String getCty53Flg() {
    return cty53Flg;
  }

  public void setCty53Flg(String cty53Flg) {
    this.cty53Flg = cty53Flg;
  }

  public String getCty54Flg() {
    return cty54Flg;
  }

  public void setCty54Flg(String cty54Flg) {
    this.cty54Flg = cty54Flg;
  }

  public String getCty55Flg() {
    return cty55Flg;
  }

  public void setCty55Flg(String cty55Flg) {
    this.cty55Flg = cty55Flg;
  }

  public String getCty56Flg() {
    return cty56Flg;
  }

  public void setCty56Flg(String cty56Flg) {
    this.cty56Flg = cty56Flg;
  }

  public String getCty57Flg() {
    return cty57Flg;
  }

  public void setCty57Flg(String cty57Flg) {
    this.cty57Flg = cty57Flg;
  }

  public String getCty58Flg() {
    return cty58Flg;
  }

  public void setCty58Flg(String cty58Flg) {
    this.cty58Flg = cty58Flg;
  }

  public String getCty59Flg() {
    return cty59Flg;
  }

  public void setCty59Flg(String cty59Flg) {
    this.cty59Flg = cty59Flg;
  }

  public String getCty60Flg() {
    return cty60Flg;
  }

  public void setCty60Flg(String cty60Flg) {
    this.cty60Flg = cty60Flg;
  }

  public String getCty61Flg() {
    return cty61Flg;
  }

  public void setCty61Flg(String cty61Flg) {
    this.cty61Flg = cty61Flg;
  }

  public String getCty62Flg() {
    return cty62Flg;
  }

  public void setCty62Flg(String cty62Flg) {
    this.cty62Flg = cty62Flg;
  }

  public String getCty63Flg() {
    return cty63Flg;
  }

  public void setCty63Flg(String cty63Flg) {
    this.cty63Flg = cty63Flg;
  }

  public LocalDate getDeleteDt() {
    return deleteDt;
  }

  public void setDeleteDt(LocalDate deleteDt) {
    this.deleteDt = deleteDt;
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public Serializable getPrimaryKey() {
    return new CountyOwnershipPK(entityId, entityCd);
  }
}
