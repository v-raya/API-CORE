package gov.ca.cwds.data.legacy.cms.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author CWDS CALS API Team
 */
public class SubCareProviderPhoneticNamePK implements Serializable {

  private static final long serialVersionUID = 5116558959190291210L;

  @Column(name = "PHONETC_NM", nullable = false, length = 8)
  @Id
  private String phonetcNm;

  @Column(name = "FKSB_PVDRT", nullable = false, length = 10)
  @Id
  private String fksbPvdrt;

  public SubCareProviderPhoneticNamePK() {
  }

  public SubCareProviderPhoneticNamePK(String phonetcNm, String fksbPvdrt) {
    this.phonetcNm = phonetcNm;
    this.fksbPvdrt = fksbPvdrt;
  }

  public String getPhonetcNm() {
    return phonetcNm;
  }

  public void setPhonetcNm(String phonetcNm) {
    this.phonetcNm = phonetcNm;
  }

  public String getFksbPvdrt() {
    return fksbPvdrt;
  }

  public void setFksbPvdrt(String fksbPvdrt) {
    this.fksbPvdrt = fksbPvdrt;
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
