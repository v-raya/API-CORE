package gov.ca.cwds.cms.data.access.service.impl.relationships;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientPaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R09476Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME_1 = "R-09476-1";
  private static final String RULE_NAME_2 = "R-09476-2";

  public static final String PRIMARY_CLIENT_ID = "0101010101";
  public static final String SECONDARY_CLIENT_ID = "101010101010";

  @Test
  public void testRelationShipFatherSonWithSameId() {
    prepareParentChildSameId((short) 205);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipFatherSonWithDifferentId() {
    prepareParentChildDifferentId((short) 205);
    checkRuleViolatedOnce(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipFatherDaughterWithSameId() {
    prepareParentChildSameId((short) 211);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipFatherDaughterWithDifferentId() {
    prepareParentChildDifferentId((short) 211);
    checkRuleViolatedOnce(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationShipSonFatherWithSameId() {
    prepareChildParentSameId((short) 190);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipSonFatherWithDifferentId() {
    prepareChildParentDifferentId((short) 190);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleViolatedOnce(RULE_NAME_2);
  }

  @Test
  public void testRelationshipDaughterFatherWithSameId() {
    prepareChildParentSameId((short) 285);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipDaughterFatherWithDifferentId() {
    prepareChildParentDifferentId((short) 285);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleViolatedOnce(RULE_NAME_2);
  }

  @Test
  public void testRelationshipOtherType() {
    prepareChildParentDifferentId((short) 44);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  private void prepareParentChildSameId(Short type) {
    ClientRelationship relationship =
        clientRelationshipBuilder.apply(PRIMARY_CLIENT_ID, SECONDARY_CLIENT_ID);
    setRelationshipType.accept(type, relationship);
    ClientPaternityDetail paternityDetail = paternityDetailBuilder.apply(relationship, YesNoUnknown.YES);
    paternityDetail.setClient(relationship.getPrimaryClient());

    awareDTO.setEntity(relationship);
    awareDTO.getSecondaryClientPaternityDetails().add(paternityDetail);
  }

  private void prepareParentChildDifferentId(Short type) {
    ClientRelationship relationship =
        clientRelationshipBuilder.apply(PRIMARY_CLIENT_ID, SECONDARY_CLIENT_ID);
    setRelationshipType.accept(type, relationship);
    ClientPaternityDetail paternityDetail = paternityDetailBuilder.apply(relationship, YesNoUnknown.YES);

    Client client = new Client();
    client.setIdentifier("0011223344");
    paternityDetail.setClient(client);

    awareDTO.setEntity(relationship);
    awareDTO.getSecondaryClientPaternityDetails().add(paternityDetail);
  }

  private void prepareChildParentSameId(Short type) {
    ClientRelationship relationship =
        clientRelationshipBuilder.apply(PRIMARY_CLIENT_ID, SECONDARY_CLIENT_ID);
    setRelationshipType.accept(type, relationship);
    ClientPaternityDetail paternityDetail = paternityDetailBuilder.apply(relationship, YesNoUnknown.YES);
    paternityDetail.setClient(relationship.getSecondaryClient());

    awareDTO.setEntity(relationship);
    awareDTO.getPrimaryClientPaternityDetails().add(paternityDetail);
  }

  private void prepareChildParentDifferentId(Short type) {
    ClientRelationship relationship =
        clientRelationshipBuilder.apply(PRIMARY_CLIENT_ID, SECONDARY_CLIENT_ID);
    setRelationshipType.accept(type, relationship);
    ClientPaternityDetail paternityDetail = paternityDetailBuilder.apply(relationship, YesNoUnknown.YES);

    Client client = new Client();
    client.setIdentifier("0011223344");
    paternityDetail.setClient(client);

    awareDTO.setEntity(relationship);
    awareDTO.getPrimaryClientPaternityDetails().add(paternityDetail);
  }

  private BiFunction<String, String, ClientRelationship> clientRelationshipBuilder =
      (primaryId, secondaryId) -> {
        ClientRelationship clientRelationship = new ClientRelationship();

        Client primaryClient = new Client();
        primaryClient.setIdentifier(primaryId);
        clientRelationship.setPrimaryClient(primaryClient);

        Client secondaryClient = new Client();
        secondaryClient.setIdentifier(secondaryId);
        clientRelationship.setSecondaryClient(secondaryClient);

        return clientRelationship;
      };

  private BiFunction<ClientRelationship, YesNoUnknown, ClientPaternityDetail> paternityDetailBuilder =
      (relationship, birthFatherCode) -> {
        ClientPaternityDetail paternityDetail = new ClientPaternityDetail();
        paternityDetail.setBirthFatherStatus(birthFatherCode);
        return paternityDetail;
      };

  private BiConsumer<Short, ClientRelationship> setRelationshipType = (type, relationship) -> {
    ClientRelationshipType clientRelationshipType = new ClientRelationshipType();
    clientRelationshipType.setSystemId(type);
    relationship.setType(clientRelationshipType);
  };
}
