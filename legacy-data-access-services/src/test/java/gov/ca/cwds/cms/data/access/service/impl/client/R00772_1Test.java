package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.ClientPaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R00772_1Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00772";
  private static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);
  private static final String CHILD_CLIENT_ID = "CCCCCCCCCC";

  @Test
  public void testPaternityHearingDateAndBirthDateAreNulls() throws Exception {
    checkRuleSatisfied(
        dto(childClient(null), paternityDetail("AAAAAAAAAA", null)),
        RULE_NAME);
  }

  @Test
  public void testBirthDateIsNull() throws Exception {
    checkRuleSatisfied(dto(childClient(null), paternityDetail("AAAAAAAAAA", SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testPaternityHearingDateIsNull() throws Exception {
    checkRuleSatisfied(
        dto(childClient(SOME_DATE), paternityDetail("AAAAAAAAAA", null)),
        RULE_NAME);
  }

  @Test
  public void testPaternityHearingDateGtBirthDate() throws Exception {
    checkRuleSatisfied(
        dto(childClient(SOME_DATE), paternityDetail("AAAAAAAAAA", SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testPaternityHearingDateLtBirthDate() throws Exception {
    checkRuleViolatedOnce(
        dto(childClient(SOME_DATE), paternityDetail("AAAAAAAAAA", SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTwoPaternityHearingDatesLtBirthDate() throws Exception {
    checkRuleViolated(dto(childClient(SOME_DATE),
        paternityDetail("AAAAAAAAAA", SOME_DATE.minusDays(1)),
        paternityDetail("BBBBBBBBBB", SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testPaternityHearingDateEqBirthDate() throws Exception {
    checkRuleSatisfied(dto(childClient(SOME_DATE), paternityDetail("AAAAAAAAAA", SOME_DATE)),
        RULE_NAME);
  }

  private static ChildClient childClient(LocalDate birthDate) {
    ChildClient childClient = new ChildClient();
    childClient.setIdentifier(CHILD_CLIENT_ID);
    childClient.setBirthDate(birthDate);
    return childClient;
  }

  private static PaternityDetail paternityDetail(String id, LocalDate date) {
    PaternityDetail paternityDetail = new ClientPaternityDetail();
    paternityDetail.setId(id);
    paternityDetail.setPaternityHearingDate(date);
    return paternityDetail;
  }

  private static ChildClientEntityAwareDTO dto(ChildClient childClient,
      PaternityDetail... paternityDetails) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(childClient);
    dto.setPaternityDetails(Arrays.asList(paternityDetails));
    return dto;
  }
}
