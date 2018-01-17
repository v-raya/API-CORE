package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CLN_RELT")
public class ClientRelationship extends CmsPersistentObject {

  @Id
  @Column(name = "IDENTIFIER", nullable = false, length = 10)
  private String identifier;

  @Column(name = "ABSENT_CD", nullable = false, length = 1)
  private String absentCd;

  @Column(name = "CLNTRELC", nullable = false)
  private short clntrelc;

  @Column(name = "END_DT", nullable = true)
  private Date endDt;

  @Column(name = "SAME_HM_CD", nullable = false, length = 1)
  private String sameHmCd;

  @Column(name = "START_DT", nullable = true)
  private Date startDt;

  @Column(name = "FKCLIENT_T", nullable = false, length = 10)
  private String fkclientT;

  @Column(name = "FKCLIENT_0", nullable = false, length = 10)
  private String fkclient0;

  @Override
  public Serializable getPrimaryKey() {
    return identifier;
  }


  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }


  public String getAbsentCd() {
    return absentCd;
  }

  public void setAbsentCd(String absentCd) {
    this.absentCd = absentCd;
  }


  public short getClntrelc() {
    return clntrelc;
  }

  public void setClntrelc(short clntrelc) {
    this.clntrelc = clntrelc;
  }


  public Date getEndDt() {
    return endDt;
  }

  public void setEndDt(Date endDt) {
    this.endDt = endDt;
  }


  public String getSameHmCd() {
    return sameHmCd;
  }

  public void setSameHmCd(String sameHmCd) {
    this.sameHmCd = sameHmCd;
  }


  public Date getStartDt() {
    return startDt;
  }

  public void setStartDt(Date startDt) {
    this.startDt = startDt;
  }


  public String getFkclientT() {
    return fkclientT;
  }

  public void setFkclientT(String fkclientT) {
    this.fkclientT = fkclientT;
  }

  public String getFkclient0() {
    return fkclient0;
  }

  public void setFkclient0(String fkclient0) {
    this.fkclient0 = fkclient0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientRelationship clnRelt = (ClientRelationship) o;
    return clntrelc == clnRelt.clntrelc &&
        Objects.equals(identifier, clnRelt.identifier) &&
        Objects.equals(absentCd, clnRelt.absentCd) &&
        Objects.equals(endDt, clnRelt.endDt) &&
        Objects.equals(sameHmCd, clnRelt.sameHmCd) &&
        Objects.equals(startDt, clnRelt.startDt) &&
        Objects.equals(fkclientT, clnRelt.fkclientT) &&
        Objects.equals(fkclient0, clnRelt.fkclient0);
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(identifier, absentCd, clntrelc, endDt, sameHmCd, startDt,
            fkclientT,
            fkclient0);
  }

}
