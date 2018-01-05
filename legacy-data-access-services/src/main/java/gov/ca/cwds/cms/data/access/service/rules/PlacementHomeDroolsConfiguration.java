package gov.ca.cwds.cms.data.access.service.rules;

import gov.ca.cwds.data.legacy.cms.entity.PlacementHome;
import gov.ca.cwds.drools.DroolsConfiguration;

/**
 * @author CWDS CALS API Team
 */

public final class PlacementHomeDroolsConfiguration extends DroolsConfiguration<PlacementHome> {

  public static final PlacementHomeDroolsConfiguration INSTANCE = new PlacementHomeDroolsConfiguration(
      "placement-home-session",
      "placement-home-agenda",
      "placement-home-legacy-rules"
  );

  public static final PlacementHomeDroolsConfiguration DATA_PROCESSING_INSTANCE = new PlacementHomeDroolsConfiguration(
          "placement-home-session",
          "placement-home-data-processi-gagenda",
          "placement-home-legacy-rules"
  );

  public PlacementHomeDroolsConfiguration(String rulesSession, String rulesAgenda,
      String kieContainerId) {
    super(rulesSession, rulesAgenda, kieContainerId);
  }
}
