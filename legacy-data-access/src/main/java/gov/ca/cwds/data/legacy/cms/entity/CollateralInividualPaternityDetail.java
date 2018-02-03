package gov.ca.cwds.data.legacy.cms.entity;

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

  public String getCollateralInividualId() {
    return collateralInividualId;
  }

  public void setCollateralInividualId(String collateralInividualId) {
    this.collateralInividualId = collateralInividualId;
  }
}
