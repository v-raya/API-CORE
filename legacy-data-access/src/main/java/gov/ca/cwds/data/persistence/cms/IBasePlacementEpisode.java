package gov.ca.cwds.data.persistence.cms;

import java.util.Set;

/**
 * @author CWDS CALS API Team
 */
public interface IBasePlacementEpisode<H extends BaseOutOfHomePlacement, S extends BaseStaffPerson> {

  Set<H> getOutOfHomePlacements();

  S getStaffPerson();
}
