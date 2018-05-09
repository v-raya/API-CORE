package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R00753Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME = "R-00753";

  /**
   *
   *      x-------------x
   *        x-------------x
   */
  @Test
  public void overlap1() {

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
   *
   *      x-------------x
   *    x------------x
   */
  @Test
  public void overlap2() {
    LocalDate localDateStartA = LocalDate.of(2001, 11, 11);
    LocalDate localDateEndA = LocalDate.of(2020, 11, 11);

    ClientRelationship entity = getRelationship(localDateStartA, localDateEndA);
    awareDTO.setEntity(entity);

    List<ClientRelationship> relationships = new ArrayList<>();

    ClientRelationship overlapRelationship1 =
        getRelationship(localDateStartA.minusYears(1), localDateEndA.minusYears(1));

    relationships.add(overlapRelationship1);

    relationships.add(
        getRelationship(localDateStartA.minusYears(3), localDateStartA.minusYears(2)));
    relationships.add(getRelationship(localDateEndA.plusYears(3), localDateEndA.plusYears(4)));

    awareDTO.getClientRelationshipList().addAll(relationships);

    checkRuleViolatedOnce(RULE_NAME);
  }

  /**
   *
   *      x-------------x
   *      x-------------x
   */
  @Test
  public void overlap3() {
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
   *     <p>x-------------x
   *          x--------x
   */
  @Test
  public void overlap4() {
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
   *
   *         x-------------x
   *   x-----x
   */
  @Test
  public void overlap5() {
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
   *     x-------------x
   *                      x-------x
   */
  @Test
  public void overlap6() {
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
   *     <p>x-------------x
   *                        x-------------x
   */
  @Test
  public void noOverlap1() {
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
   *          x-------------x
   *  x-----x
   */
  @Test
  public void noOverlap2() {
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
}
