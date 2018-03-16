package gov.ca.cwds.cms.data.access.service.impl.relationships;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.drools.DroolsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R00753Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME = "R-00753";

  /**
   * @throws DroolsException
   *
   *      x-------------x
   *        x-------------x
   */
  @Test
  public void overlap1() throws DroolsException {

    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = new ClientRelationship();
    overlapRelationship1.setStartDate(localDateStartA.plusYears(1));
    overlapRelationship1.setEndDate(localDateEndA.plusYears(1));
    relationships.add(overlapRelationship1);

    relationships.add(getRelationship(localDateStartA.minusYears(3),localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3),localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *
   *      x-------------x
   *    x------------x
   */
  @Test
  public void overlap2() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = new ClientRelationship();
    overlapRelationship1.setStartDate(localDateStartA.minusYears(1));
    overlapRelationship1.setEndDate(localDateEndA.minusYears(1));
    relationships.add(overlapRelationship1);

    relationships.add(getRelationship(localDateStartA.minusYears(3),localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3),localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *
   *      x-------------x
   *      x-------------x
   */
  @Test
  public void overlap3() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = new ClientRelationship();
    overlapRelationship1.setStartDate(localDateStartA);
    overlapRelationship1.setEndDate(localDateEndA);
    relationships.add(overlapRelationship1);

    relationships.add(getRelationship(localDateStartA.minusYears(3),localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3),localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *
   *      x-------------x
   *        x--------x
   */
  @Test
  public void overlap4() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = new ClientRelationship();
    overlapRelationship1.setStartDate(localDateStartA.minusYears(2));
    overlapRelationship1.setEndDate(localDateEndA.minusYears(2));
    relationships.add(overlapRelationship1);

    relationships.add(getRelationship(localDateStartA.minusYears(3),localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3),localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *
   *         x-------------x
   *   x-----x
   */
  @Test
  public void overlap5() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = new ClientRelationship();
    overlapRelationship1.setStartDate(localDateStartA.minusYears(2));
    overlapRelationship1.setEndDate(localDateStartA);
    relationships.add(overlapRelationship1);

    relationships.add(getRelationship(localDateStartA.minusYears(3),localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3),localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *
   *      x-------------x
   *                    x-------x
   */
  @Test
  public void overlap6() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = new ClientRelationship();
    overlapRelationship1.setStartDate(localDateEndA);
    overlapRelationship1.setEndDate(localDateEndA.plusYears(2));
    relationships.add(overlapRelationship1);

    relationships.add(getRelationship(localDateStartA.minusYears(3),localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3),localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   * @throws DroolsException
   *
   *      x-------------x
   *                      x-------------x
   */
  @Test
  public void noOverlap1() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = new ClientRelationship();
    overlapRelationship1.setStartDate(localDateEndA.plusDays(1));
    overlapRelationship1.setEndDate(localDateEndA.plusYears(2));
    relationships.add(overlapRelationship1);

    relationships.add(getRelationship(localDateStartA.minusYears(3),localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3),localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleSatisfied(RULE_NAME);
  }


  /**
   * @throws DroolsException
   *
   *          x-------------x
   *  x-----x
   */
  @Test
  public void noOverlap2() throws DroolsException {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 = new ClientRelationship();
    overlapRelationship1.setStartDate(localDateStartA.minusYears(4));
    overlapRelationship1.setEndDate(localDateStartA.minusDays(1));
    relationships.add(overlapRelationship1);

    relationships.add(getRelationship(localDateStartA.minusYears(3),localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3),localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleSatisfied(RULE_NAME);
  }

  private ClientRelationship getRelationship(LocalDate startDate, LocalDate endDate) {
    ClientRelationship clientRelationship = new ClientRelationship();
    clientRelationship.setStartDate(startDate);
    clientRelationship.setEndDate(endDate);
    return clientRelationship;
  }
}
