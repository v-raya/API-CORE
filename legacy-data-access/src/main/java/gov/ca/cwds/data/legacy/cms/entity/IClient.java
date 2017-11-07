package gov.ca.cwds.data.legacy.cms.entity;

import java.util.Set;

/**
 * @author CWDS CALS API Team
 */
@SuppressWarnings("squid:S1609") // this interface is not meant to be functional
public interface IClient<T extends BasePlacementEpisode> {

  Set<T> getPlacementEpisodes();
}
