package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 */
@SuppressWarnings({"squid:S3437"}) //LocalDate is serializable
public class ExternalInterfacePK implements Serializable {

  private static final long serialVersionUID = -5937783687228572512L;

  @Column(name = "SEQ_NO", nullable = false)
  @Id
  private int seqNo;

  @Column(name = "SUBMTL_TS", nullable = false)
  @Id
  private LocalDateTime submtlTs;

  @Column(name = "L_USERID", nullable = false, length = 8)
  @Id
  private String lUserid;

  public ExternalInterfacePK() {
  }

  public ExternalInterfacePK(int seqNo, LocalDateTime submtlTs, String lUserid) {
    this.seqNo = seqNo;
    this.submtlTs = submtlTs;
    this.lUserid = lUserid;
  }

  public int getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(int seqNo) {
    this.seqNo = seqNo;
  }

  public LocalDateTime getSubmtlTs() {
    return submtlTs;
  }

  public void setSubmtlTs(LocalDateTime submtlTs) {
    this.submtlTs = submtlTs;
  }

  public String getlUserid() {
    return lUserid;
  }

  public void setlUserid(String lUserid) {
    this.lUserid = lUserid;
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
