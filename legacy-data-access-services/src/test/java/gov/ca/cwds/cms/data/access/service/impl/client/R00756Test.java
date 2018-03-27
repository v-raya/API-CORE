package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R00756Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00756";

  private static final String CLIENT_ID = "0123456789";

  public static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);

  @Test
  public void testPrimaryRelationshipStartDateGtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(
        dto(client, primaryRelationship(
            "Aaqj06L00h", client, "AapJGAU04Z", SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testSecondaryRelationshipStartDateGtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(
        dto(client,
            secondaryRelationship(
                "Aaqj06L00h", "AapJGAU04Z", client, SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testPrimaryRelationshipStartDateEqBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(
        dto(client,
            primaryRelationship("Aaqj06L00h", client, "AapJGAU04Z", SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testSecondaryRelationshipStartDateEqBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(
        dto(client,
            secondaryRelationship("Aaqj06L00h", "AapJGAU04Z", client, SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testPrimaryRelationshipStartDateLtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleViolatedOnce(
        dto(client,
            primaryRelationship(
                "Aaqj06L00h", client, "AapJGAU04Z", SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testSecondaryRelationshipStartDateLtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleViolatedOnce(
        dto(client,
            secondaryRelationship(
                "Aaqj06L00h", "AapJGAU04Z", client, SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testPrimaryRelationshipBirthDateNull() throws Exception {
    Client client = client(null);
    checkRuleSatisfied(
        dto(client,
            primaryRelationship(
                "Aaqj06L00h", client, "AapJGAU04Z", SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testSecondaryRelationshipBirthDateNull() throws Exception {
    Client client = client(null);
    checkRuleSatisfied(
        dto(client,
            secondaryRelationship(
                "Aaqj06L00h", "AapJGAU04Z", client, SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testPrimaryRelationshipStartDateNull() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(
        dto(client, primaryRelationship(
            "Aaqj06L00h", client, "AapJGAU04Z", null)),
        RULE_NAME);
  }

  @Test
  public void testSecondaryRelationshipStartDateNull() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(
        dto(client,
            secondaryRelationship(
                "Aaqj06L00h", "AapJGAU04Z", client, null)),
        RULE_NAME);
  }

  @Test
  public void testTwoRelationshipsStartDateLtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleViolated(dto(client,
        primaryRelationship(
            "Aaqj06L00h", client, "AckXIhU0QO", SOME_DATE.minusDays(1)),
        secondaryRelationship(
            "AasRx3r0Ha", "AcqEeTo0Ql", client, SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }


  private static ClientEntityAwareDTO dto(Client client, ClientRelationship... relationships) {
    ClientEntityAwareDTO dto = new ClientEntityAwareDTO();
    dto.setEntity(client);
    dto.getClientRelationships().addAll(Arrays.asList(relationships));
    return dto;
  }

  public static Client client(LocalDate birthDay) {
    Client client = new Client();
    client.setIdentifier(CLIENT_ID);
    client.setBirthDate(birthDay);
    return client;
  }

  public static Client otherClient(String id) {
    Client client = new Client();
    client.setIdentifier(id);
    return client;
  }

  private static ClientRelationship primaryRelationship(String id, Client client,
      String otherClientId, LocalDate startDate
  ) {
    return clientRelationship(id, client, otherClient(otherClientId), startDate);
  }

  private static ClientRelationship secondaryRelationship(String id, String otherClientId,
      Client client,
      LocalDate startDate
  ) {
    return clientRelationship(id, otherClient(otherClientId), client, startDate);
  }

  private static ClientRelationship clientRelationship(String id, Client primaryClient,
      Client secondaryClient, LocalDate startDate) {
    ClientRelationship relationship = new ClientRelationship();
    relationship.setIdentifier(id);
    relationship.setPrimaryClient(primaryClient);
    relationship.setSecondaryClient(secondaryClient);
    relationship.setStartDate(startDate);
    return relationship;
  }
}
