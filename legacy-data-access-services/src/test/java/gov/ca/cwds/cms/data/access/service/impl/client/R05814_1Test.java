package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.SOME_DATE;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R05814_1Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-05814";

  @Test
  public void testRemovalDateGtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(
        dto(client, placementEpisode(client, SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testRemovalDateLtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleViolatedOnce(
        dto(client, placementEpisode(client, SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTwoRemovalDatesLtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleViolated(dto(client,
        placementEpisode(client, SOME_DATE.minusDays(1)),
        placementEpisode(client, SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testRemovalDateEqBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(dto(client, placementEpisode(client, SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    Client client = client(null);
    checkRuleSatisfied(dto(client, placementEpisode(client, SOME_DATE)),
        RULE_NAME);
  }

  public static PlacementEpisode placementEpisode(Client parent, LocalDate removalDate) {
    PlacementEpisode placementEpisode = new PlacementEpisode();
    placementEpisode.setFkclientT(parent.getIdentifier());

    placementEpisode.setRemovalDt(removalDate);
    return placementEpisode;
  }

  public static Client client(LocalDate birthDate) {
    Client client = new Client();
    client.setIdentifier("ChIlDS40FG");
    client.setBirthDate(birthDate);
    return client;
  }

  public static ClientEntityAwareDTO dto(Client client, PlacementEpisode... placementEpisodes) {
    ClientEntityAwareDTO dto = new ClientEntityAwareDTO();
    dto.setEntity(client);
    dto.getPlacementEpisodes().addAll(Arrays.asList(placementEpisodes));
    return dto;
  }
}
