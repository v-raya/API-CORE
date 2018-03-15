package gov.ca.cwds.data.legacy.cms.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Table(name = "PLC_EPST")
public class PlacementEpisode extends BasePlacementEpisode {

  private static final long serialVersionUID = -3903845942588945920L;

  private Set<OutOfHomePlacement> outOfHomePlacements = new HashSet<>();

  @Override
  @OneToMany
  @JoinColumn(name = "FKPLC_EPS0", referencedColumnName = "THIRD_ID")
  @OrderBy("startDt DESC")
  public Set<OutOfHomePlacement> getOutOfHomePlacements() {
    return outOfHomePlacements;
  }

  public void setOutOfHomePlacements(Set<OutOfHomePlacement> outOfHomePlacements) {
    this.outOfHomePlacements = outOfHomePlacements;
  }

  @Override
  @Transient
  // this logic needs to be reworked, StaffPerson is not the person who adds/removes child from
//  placement home
//  @NotFound(action = NotFoundAction.IGNORE)
//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "RMV_BY_ID", referencedColumnName = "IDENTIFIER")
  public StaffPerson getStaffPerson() {
    return null;
  }
}
