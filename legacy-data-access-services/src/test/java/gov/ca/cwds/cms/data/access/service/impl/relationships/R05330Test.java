package gov.ca.cwds.cms.data.access.service.impl.relationships;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R05330Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME = "R-05330";

  @Test
  public void twoRelationshipsWitOneActive() throws Exception {
    List<ClientRelationship> relationships = new ArrayList<>();
    relationships.add(getRelationship(LocalDate.of(2001, 1, 1), null));
    relationships.add(getRelationship(LocalDate.of(1999, 1, 1), LocalDate.of(2000, 1, 1)));

    awareDTO.setEntity(getRelationship(LocalDate.of(2002, 1, 1), null));
    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void twoRelationshipsWitNoActive() throws Exception {
    List<ClientRelationship> relationships = new ArrayList<>();
    relationships.add(getRelationship(LocalDate.of(2001, 1, 1), LocalDate.of(2002, 1, 1)));
    relationships.add(getRelationship(LocalDate.of(1999, 1, 1), LocalDate.of(2000, 1, 1)));

    awareDTO.setEntity(getRelationship(LocalDate.of(2003, 1, 1), null));
    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void oneRelationshipActive() throws Exception {
    List<ClientRelationship> relationships = new ArrayList<>();
    relationships.add(getRelationship(LocalDate.of(2001, 1, 1), null));

    awareDTO.setEntity(getRelationship(LocalDate.of(2002, 1, 1), null));
    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void oneRelationshipNoActive() throws Exception {
    List<ClientRelationship> relationships = new ArrayList<>();
    relationships.add(getRelationship(LocalDate.of(2001, 1, 1), LocalDate.of(2002, 1, 1)));

    awareDTO.setEntity(getRelationship(LocalDate.of(2003, 1, 1), null));
    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void updatedRelationshipIsNotActive() throws Exception {
    List<ClientRelationship> relationships = new ArrayList<>();
    relationships.add(getRelationship(LocalDate.of(2009, 1, 1), null));
    relationships.add(getRelationship(LocalDate.of(1999, 1, 1), LocalDate.of(2000, 1, 1)));

    awareDTO.setEntity(getRelationship(LocalDate.of(2002, 1, 1), LocalDate.of(2003, 1, 1)));
    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleSatisfied(RULE_NAME);
  }
}
