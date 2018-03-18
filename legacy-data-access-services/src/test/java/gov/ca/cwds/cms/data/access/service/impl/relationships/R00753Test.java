package gov.ca.cwds.cms.data.access.service.impl.relationships;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.drools.DroolsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R00753Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME = "R-00753";

  private String PRIMARY_ID = "0001112223";
  private String SECONDARY_ID = "0001112224";

  /**
   * @throws DroolsException
   *     <p>x-------------x x-------------x
   */
  @Test
  public void overlap1() throws DroolsException {

    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 =
        getRelationship(localDateStartA.plusYears(1), localDateEndA.plusYears(1));
    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *     <p>x-------------x x------------x
   */
  @Test
  public void overlap2() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 =
        getRelationship(localDateStartA.minusYears(1), localDateEndA.minusYears(1));
    ;
    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *     <p>x-------------x x-------------x
   */
  @Test
  public void overlap3() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = getRelationship(localDateStartA, localDateEndA);
    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *     <p>x-------------x x--------x
   */
  @Test
  public void overlap4() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 =
        getRelationship(localDateStartA.minusYears(2), localDateEndA.minusYears(2));
    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *     <p>x-------------x x-----x
   */
  @Test
  public void overlap5() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 =
        getRelationship(localDateStartA.minusYears(2), localDateStartA);
    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *     <p>x-------------x x-------x
   */
  @Test
  public void overlap6() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 =
        getRelationship(localDateEndA, localDateEndA.plusYears(2));
    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *     <p>x-------------x x-------------x
   */
  @Test
  public void noOverlap1() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 =
        getRelationship(localDateEndA.plusDays(1), localDateEndA.plusYears(2));
    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleSatisfied(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *     <p>x-------------x x-----x
   */
  @Test
  public void noOverlap2() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 =
        getRelationship(localDateStartA.minusYears(4), localDateStartA.minusDays(1));
    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleSatisfied(RULE_NAME);
  }

  private ClientRelationship getRelationship(LocalDate startDate, LocalDate endDate) {
    ClientRelationship clientRelationship = new ClientRelationship();
    clientRelationship.setStartDate(startDate);
    clientRelationship.setEndDate(endDate);

    Client client = new Client();
    client.setIdentifier(PRIMARY_ID);

    Client clientSecondary = new Client();
    clientSecondary.setIdentifier(SECONDARY_ID);

    clientRelationship.setRightSide(client);
    clientRelationship.setLeftSide(clientSecondary);

    return clientRelationship;
  }
}
