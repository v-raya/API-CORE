package gov.ca.cwds.data.legacy.cms.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HLTH_RFT")
public class HlthRft {

  private String fkchldClt;
  private String thirdId;
  private short hlRefc;
  private Date hlRefDt;
  private Short hlCnstc;
  private Date hlCnstDt;
  private short hlRef2C;
  private String comntDsc;
  private String hlRefoCd;
  private Date hlRefoDt;
  private String hlOocInd;
  private String lstUpdId;
  private Timestamp lstUpdTs;

  @Id
  @Column(name = "FKCHLD_CLT")
  public String getFkchldClt() {
    return fkchldClt;
  }

  public void setFkchldClt(String fkchldClt) {
    this.fkchldClt = fkchldClt;
  }

  @Id
  @Column(name = "THIRD_ID")
  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  @Basic
  @Column(name = "HL_REFC")
  public short getHlRefc() {
    return hlRefc;
  }

  public void setHlRefc(short hlRefc) {
    this.hlRefc = hlRefc;
  }

  @Basic
  @Column(name = "HL_REF_DT")
  public Date getHlRefDt() {
    return hlRefDt;
  }

  public void setHlRefDt(Date hlRefDt) {
    this.hlRefDt = hlRefDt;
  }

  @Basic
  @Column(name = "HL_CNSTC")
  public Short getHlCnstc() {
    return hlCnstc;
  }

  public void setHlCnstc(Short hlCnstc) {
    this.hlCnstc = hlCnstc;
  }

  @Basic
  @Column(name = "HL_CNST_DT")
  public Date getHlCnstDt() {
    return hlCnstDt;
  }

  public void setHlCnstDt(Date hlCnstDt) {
    this.hlCnstDt = hlCnstDt;
  }

  @Basic
  @Column(name = "HL_REF2C")
  public short getHlRef2C() {
    return hlRef2C;
  }

  public void setHlRef2C(short hlRef2C) {
    this.hlRef2C = hlRef2C;
  }

  @Basic
  @Column(name = "COMNT_DSC")
  public String getComntDsc() {
    return comntDsc;
  }

  public void setComntDsc(String comntDsc) {
    this.comntDsc = comntDsc;
  }

  @Basic
  @Column(name = "HL_REFO_CD")
  public String getHlRefoCd() {
    return hlRefoCd;
  }

  public void setHlRefoCd(String hlRefoCd) {
    this.hlRefoCd = hlRefoCd;
  }

  @Basic
  @Column(name = "HL_REFO_DT")
  public Date getHlRefoDt() {
    return hlRefoDt;
  }

  public void setHlRefoDt(Date hlRefoDt) {
    this.hlRefoDt = hlRefoDt;
  }

  @Basic
  @Column(name = "HL_OOC_IND")
  public String getHlOocInd() {
    return hlOocInd;
  }

  public void setHlOocInd(String hlOocInd) {
    this.hlOocInd = hlOocInd;
  }

  @Basic
  @Column(name = "LST_UPD_ID")
  public String getLstUpdId() {
    return lstUpdId;
  }

  public void setLstUpdId(String lstUpdId) {
    this.lstUpdId = lstUpdId;
  }

  @Basic
  @Column(name = "LST_UPD_TS")
  public Timestamp getLstUpdTs() {
    return lstUpdTs;
  }

  public void setLstUpdTs(Timestamp lstUpdTs) {
    this.lstUpdTs = lstUpdTs;
  }
}
