package gov.ca.cwds.data.persistence.cms;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 */
public class AddressPhoneticNamePK implements Serializable {

  private static final long serialVersionUID = -5529227592027434298L;
  
  @Id
  @Column(name = "PHONETC_NM", nullable = false, length = 8)
  private String phonetcNm;

  @Id
  @Column(name = "PRMRY_NMID", nullable = false, length = 10)
  private String prmryNmid;

  @Id
  @Column(name = "PRMRY_NMCD", nullable = false, length = 1)
  private String prmryNmcd;

  public AddressPhoneticNamePK() {
  }

  public AddressPhoneticNamePK(String phonetcNm, String prmryNmid, String prmryNmcd) {
    this.phonetcNm = phonetcNm;
    this.prmryNmid = prmryNmid;
    this.prmryNmcd = prmryNmcd;
  }

  public String getPhonetcNm() {
    return phonetcNm;
  }

  public void setPhonetcNm(String phonetcNm) {
    this.phonetcNm = phonetcNm;
  }

  public String getPrmryNmid() {
    return prmryNmid;
  }

  public void setPrmryNmid(String prmryNmid) {
    this.prmryNmid = prmryNmid;
  }

  public String getPrmryNmcd() {
    return prmryNmcd;
  }

  public void setPrmryNmcd(String prmryNmcd) {
    this.prmryNmcd = prmryNmcd;
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
