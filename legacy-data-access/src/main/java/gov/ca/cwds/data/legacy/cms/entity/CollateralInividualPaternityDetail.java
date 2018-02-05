package gov.ca.cwds.data.legacy.cms.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@DiscriminatorValue(value = "I")
public class CollateralInividualPaternityDetail extends PaternityDetail {

  private static final long serialVersionUID = -2686848036153623448L;

  //TODO: map to CollateralInividual when such entity will be created
  @NotNull
  @Size(max = CMS_ID_LEN)
  @Column(name = "PRMY_IDVID")
  private String collateralInividualId;

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CollateralInividualPaternityDetail)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    CollateralInividualPaternityDetail that = (CollateralInividualPaternityDetail) o;
    return Objects.equals(getCollateralInividualId(), that.getCollateralInividualId()) &&
        Objects.equals(getChildClient(), that.getChildClient()) &&
        Objects.equals(getPaternityTestDate(), that.getPaternityTestDate()) &&
        Objects
            .equals(getEstablishedLocationDescription(), that.getEstablishedLocationDescription());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(super.hashCode(), getCollateralInividualId(), getChildClient(), getPaternityTestDate(),
        getEstablishedLocationDescription());
  }

  public String getCollateralInividualId() {
    return collateralInividualId;
  }

  public void setCollateralInividualId(String collateralInividualId) {
    this.collateralInividualId = collateralInividualId;
  }
}
