package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import java.time.LocalDate;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R00756Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME = "R-00756";

  @Test
  public void primaryClientBirthDateGtStartDate() throws Exception {
    awareDTO.setEntity(
        getClientRelationship(LocalDate.of(2000, 11, 11), LocalDate.of(2010, 11, 11)));

    awareDTO.getEntity().setPrimaryClient(getClient(LocalDate.of(2010, 11, 11)));
    awareDTO.getEntity().setSecondaryClient(getClient(LocalDate.of(1990, 11, 11)));
    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void secondaryClientBirthDateGtStartDate() throws Exception {
    awareDTO.setEntity(
        getClientRelationship(LocalDate.of(2000, 11, 11), LocalDate.of(2010, 11, 11)));

    awareDTO.getEntity().setPrimaryClient(getClient(LocalDate.of(1999, 11, 11)));
    awareDTO.getEntity().setSecondaryClient(getClient(LocalDate.of(2001, 11, 11)));
    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void primaryAndSecondaryClientBirthDateGtStartDate() throws Exception {
    awareDTO.setEntity(
        getClientRelationship(LocalDate.of(2000, 11, 11), LocalDate.of(2010, 11, 11)));

    awareDTO.getEntity().setPrimaryClient(getClient(LocalDate.of(2007, 11, 11)));
    awareDTO.getEntity().setSecondaryClient(getClient(LocalDate.of(2001, 11, 11)));
    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void primaryClientBirthDateGtStartDateSecondaryBirthDateNull() throws Exception {
    awareDTO.setEntity(
        getClientRelationship(LocalDate.of(2000, 11, 11), LocalDate.of(2010, 11, 11)));

    awareDTO.getEntity().setPrimaryClient(getClient(LocalDate.of(2007, 11, 11)));
    awareDTO.getEntity().setSecondaryClient(getClient(null));
    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void secondaryClientBirthDateGtStartDatePrimaryBirthDateNull() throws Exception {
    awareDTO.setEntity(
        getClientRelationship(LocalDate.of(2000, 11, 11), LocalDate.of(2010, 11, 11)));

    awareDTO.getEntity().setPrimaryClient(getClient(null));
    awareDTO.getEntity().setSecondaryClient(getClient(LocalDate.of(2007, 11, 11)));
    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void startDateNullPrimaryAndSecondaryAreNotNull() throws Exception {
    awareDTO.setEntity(getClientRelationship(null, LocalDate.of(2010, 11, 11)));

    awareDTO.getEntity().setPrimaryClient(getClient(null));
    awareDTO.getEntity().setSecondaryClient(getClient(null));
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void startDateGtPrimaryandSecondaryBirthDates() throws Exception {
    awareDTO.setEntity(
        getClientRelationship(LocalDate.of(2000, 11, 11), LocalDate.of(2010, 11, 11)));

    awareDTO.getEntity().setPrimaryClient(getClient(LocalDate.of(1990, 11, 11)));
    awareDTO.getEntity().setSecondaryClient(getClient(LocalDate.of(1988, 11, 11)));
    checkRuleSatisfied(RULE_NAME);
  }

  private Client getClient(LocalDate birthDate) {
    Client client = new Client();
    client.setBirthDate(birthDate);
    return client;
  }

  private ClientRelationship getClientRelationship(LocalDate startDate, LocalDate endDate) {
    ClientRelationship entity = new ClientRelationship();
    entity.setStartDate(startDate);
    entity.setEndDate(endDate);
    return entity;
  }
}
