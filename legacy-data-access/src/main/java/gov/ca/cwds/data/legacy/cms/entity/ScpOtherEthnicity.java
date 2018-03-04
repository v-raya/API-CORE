package gov.ca.cwds.data.legacy.cms.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue(value = "S")
public class ScpOtherEthnicity  extends OtherEthnicity {

  @NotNull
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @Column(name = "ESTBLSH_ID")
  private String substituteCareProviderId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ScpOtherEthnicity)) {
      return false;
    }
    ScpOtherEthnicity that = (ScpOtherEthnicity) o;
    return getEthnicityCode() == that.getEthnicityCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEthnicityCode());
  }

  public String getSubstituteCareProviderId() {
    return substituteCareProviderId;
  }

  public void setSubstituteCareProviderId(String substituteCareProviderId) {
    this.substituteCareProviderId = substituteCareProviderId;
  }
}
