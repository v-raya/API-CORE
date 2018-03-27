package gov.ca.cwds.cms.data.access.service.impl.relationships;

import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import java.time.LocalDate;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R00755Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME = "R-00755";

  @Test
  public void startDateGtEndDate() throws Exception {
    ClientRelationship entity =
        getClientRelationship(LocalDate.of(2001, 01, 01), LocalDate.of(2000, 02, 02));
    awareDTO.setEntity(entity);

    checkRuleViolatedOnce(RULE_NAME);
  }

  private ClientRelationship getClientRelationship(LocalDate startDate, LocalDate endDate) {
    ClientRelationship entity = new ClientRelationship();
    entity.setStartDate(startDate);
    entity.setEndDate(endDate);
    return entity;
  }

  @Test
  public void startDateLtEndDate() throws Exception {
    ClientRelationship entity =
        getClientRelationship(LocalDate.of(1999, 03, 01), LocalDate.of(2000, 02, 02));
    awareDTO.setEntity(entity);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testStartDateEndDate() throws Exception {
    LocalDate date = LocalDate.of(2003, 03, 01);
    ClientRelationship entity = getClientRelationship(date, date);
    awareDTO.setEntity(entity);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testEndDateNull() throws Exception {
    LocalDate date = LocalDate.of(2003, 03, 01);
    ClientRelationship entity = getClientRelationship(date, null);
    awareDTO.setEntity(entity);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testStartDateNull() throws Exception {
    LocalDate date = LocalDate.of(2003, 03, 01);
    ClientRelationship entity = getClientRelationship(null, date);
    awareDTO.setEntity(entity);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void startDateAndDateNull() throws Exception {
    LocalDate date = LocalDate.of(2003, 03, 01);
    ClientRelationship entity = getClientRelationship(null, null);
    awareDTO.setEntity(entity);

    checkRuleSatisfied(RULE_NAME);
  }
}
