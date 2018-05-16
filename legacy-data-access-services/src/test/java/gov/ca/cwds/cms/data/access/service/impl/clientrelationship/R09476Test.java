package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

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

  private static final String PRIMARY_CLIENT_ID = "0101010101";
  private static final String SECONDARY_CLIENT_ID = "101010101010";
  private static final String CLIENT_ID = "0011223344";

  private static final Short FATHER_SONT_TYPE = 205;
  private static final Short FATHER_DAUGHTER_TYPE = 211;
  private static final Short SON_FATHER_TYPE = 190;
  private static final Short DAUGHTER_FATHER_TYPE = 285;
  private static final Short OUT_OF_SCOPE_TYPE = 44;

  @Test
  public void testRelationShipFatherSonWithSameId() {
    prepareParentChildSameId(FATHER_SONT_TYPE);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipFatherSonWithDifferentId() {
    prepareParentChildDifferentId(FATHER_SONT_TYPE);
    checkRuleViolatedOnce(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipFatherDaughterWithSameId() {
    prepareParentChildSameId(FATHER_DAUGHTER_TYPE);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipFatherDaughterWithDifferentId() {
    prepareParentChildDifferentId(FATHER_DAUGHTER_TYPE);
    checkRuleViolatedOnce(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationShipSonFatherWithSameId() {
    prepareChildParentSameId(SON_FATHER_TYPE);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipSonFatherWithDifferentId() {
    prepareChildParentDifferentId(SON_FATHER_TYPE);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleViolatedOnce(RULE_NAME_2);
  }

  @Test
  public void testRelationshipDaughterFatherWithSameId() {
    prepareChildParentSameId(DAUGHTER_FATHER_TYPE);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleSatisfied(RULE_NAME_2);
  }

  @Test
  public void testRelationshipDaughterFatherWithDifferentId() {
    prepareChildParentDifferentId(DAUGHTER_FATHER_TYPE);
    checkRuleSatisfied(RULE_NAME_1);
    checkRuleViolatedOnce(RULE_NAME_2);
  }

  @Test
  public void testRelationshipOtherType() {
    prepareChildParentDifferentId(OUT_OF_SCOPE_TYPE);
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
    client.setIdentifier(CLIENT_ID);

    Client parent = new Client();
    client.setIdentifier(SECONDARY_CLIENT_ID);

    paternityDetail.setClient(client);
    awareDTO.setParent(parent);
    awareDTO.setChild(client);

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
    client.setIdentifier(CLIENT_ID);

    Client parent = new Client();
    client.setIdentifier(SECONDARY_CLIENT_ID);

    paternityDetail.setClient(client);

    awareDTO.setParent(parent);
    awareDTO.setChild(client);

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
