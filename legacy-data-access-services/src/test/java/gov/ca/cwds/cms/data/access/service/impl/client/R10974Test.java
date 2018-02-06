package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.CsecHistory;
import gov.ca.cwds.drools.DroolsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R10974Test extends BaseDocToolRulesChildClientImplementationTest {

  private static final String RULE_NAME = "R-10974";
  private static final String CLIENT_ID = "1234567891";
  private static final String CSEC_ID_1 = "1234567890";
  private static final String CSEC_ID_2 = "0987654321";
  private static LocalDate CLIENT_BIRTH_DAY = LocalDate.of(1999, 03, 14);

  @Test
  public void csecHistoryDateEqualsToTheClientBirthDateTest() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<CsecHistory> csecHistories = createListOfCsecHistories(CLIENT_BIRTH_DAY);
    clientEntityAwareDTO.getCsecHistories().addAll(csecHistories);

    checkRuleSatisfied(clientEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void csecHistoryDateGreaterThanClientBirthDateTest() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<CsecHistory> csecHistories = createListOfCsecHistories(CLIENT_BIRTH_DAY, true);
    clientEntityAwareDTO.getCsecHistories().addAll(csecHistories);

    checkRuleViolatedOnce(clientEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void csecHistoryDateLessThanClientBirthDateTest() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<CsecHistory> csecHistories = createListOfCsecHistories(CLIENT_BIRTH_DAY, false);
    clientEntityAwareDTO.getCsecHistories().addAll(csecHistories);

    checkRuleSatisfied(clientEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void childClientBirthDateDoesntExist() throws Exception {
    clientEntityAwareDTO.setEntity(createClient());

    List<CsecHistory> csecHistories = createListOfCsecHistories(CLIENT_BIRTH_DAY);
    clientEntityAwareDTO.getCsecHistories().addAll(csecHistories);

    checkRuleSatisfied(clientEntityAwareDTO, RULE_NAME);
  }

  private List<CsecHistory> createListOfCsecHistories(LocalDate clientBirthDay) {
    return createListOfCsecHistories(clientBirthDay, null);
  }

  private List<CsecHistory> createListOfCsecHistories(
      LocalDate clientBirthDay, Boolean validDates) {
    List<CsecHistory> csecHistories = new ArrayList<>();

    csecHistories.addAll(
        Arrays.asList(
            getCsecHistory(CSEC_ID_1, clientBirthDay, validDates),
            getCsecHistory(CSEC_ID_2, clientBirthDay, validDates)));
    return csecHistories;
  }

  private CsecHistory getCsecHistory(String csecId, LocalDate clientBirthDay, Boolean validDates) {
    CsecHistory csecHistory = new CsecHistory();
    csecHistory.setThirdId(csecId);
    if (validDates == null) {
      csecHistory.setStartDate(clientBirthDay);
    } else if (validDates) {
      csecHistory.setStartDate(clientBirthDay.minusYears(1));
    } else {
      csecHistory.setStartDate(clientBirthDay.plusYears(1));
    }

    return csecHistory;
  }

  private ChildClient createClient() {
    return createClient(null);
  }

  private ChildClient createClient(LocalDate birthDay) {
    ChildClient client = new ChildClient();
    client.setIdentifier(CLIENT_ID);
    client.setBirthDate(birthDay);

    return client;
  }
}
