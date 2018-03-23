package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode.FIND_BY_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode.PARAM_CLIENT_ID;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * @author CWDS CALS API Team
 */
@Entity
@NamedQuery(
    name = FIND_BY_CLIENT_ID,
    query = "SELECT pe FROM gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode pe "
        + "where pe.fkclientT =:" + PARAM_CLIENT_ID
)
@Table(name = "PLC_EPST")
public class PlacementEpisode extends BasePlacementEpisode {

  private static final long serialVersionUID = -3903845942588945920L;

  public static final String FIND_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode.findByClientId";

  public static final String PARAM_CLIENT_ID = "clientId";

  private Set<OutOfHomePlacement> outOfHomePlacements = new HashSet<>();

  @Override
  @OneToMany
  @JoinColumns({
     @JoinColumn(name = "FKPLC_EPS0", referencedColumnName = "THIRD_ID"),
     @JoinColumn(name = "FKPLC_EPST", referencedColumnName = "FKCLIENT_T")
  })
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
