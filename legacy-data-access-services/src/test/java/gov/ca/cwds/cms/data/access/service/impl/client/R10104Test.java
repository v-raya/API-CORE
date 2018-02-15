package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import java.time.LocalDate;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R10104Test extends BaseDocToolRulesChildClientImplementationTest {

  private static final String RULE_NAME = "R-10104";
  private static LocalDate CLIENT_BIRTH_DAY = LocalDate.of(1988, 12, 17);

  @Test
  public void birthDateLessThanTribalDate() throws Exception {
    ChildClient childClient =
        ClientTestUtil.childClient(CLIENT_BIRTH_DAY, ClientTestUtil.CHILD_CLIENT_ID);
    childClient.setTribalCustomaryAdoptionDate(CLIENT_BIRTH_DAY.plusYears(1));
    clientEntityAwareDTO.setEntity(childClient);
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void birthDateGraterThanTribalDate() throws Exception {
    ChildClient childClient =
        ClientTestUtil.childClient(CLIENT_BIRTH_DAY, ClientTestUtil.CHILD_CLIENT_ID);
    childClient.setTribalCustomaryAdoptionDate(CLIENT_BIRTH_DAY.minusYears(1));
    clientEntityAwareDTO.setEntity(childClient);
    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void birthDateIsNotPresent() throws Exception {
    ChildClient childClient = ClientTestUtil.childClient(ClientTestUtil.CHILD_CLIENT_ID);
    childClient.setTribalCustomaryAdoptionDate(CLIENT_BIRTH_DAY);
    clientEntityAwareDTO.setEntity(childClient);
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void tribalDateIsNotPresent() throws Exception {
    ChildClient childClient =
        ClientTestUtil.childClient(CLIENT_BIRTH_DAY, ClientTestUtil.CHILD_CLIENT_ID);
    clientEntityAwareDTO.setEntity(childClient);
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void birthDateAndTribalDateAreNotPresent() throws Exception {
    ChildClient childClient = ClientTestUtil.childClient(ClientTestUtil.CHILD_CLIENT_ID);
    clientEntityAwareDTO.setEntity(childClient);
    checkRuleSatisfied(RULE_NAME);
  }
}
