package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.data.legacy.cms.entity.CreditReportHistory;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R10787Test extends BaseDocToolRulesChildClientImplementationTest {

  private static final String RULE_NAME = "R-10787";
  private static final String CHILD_CLIENT_ID = "1234567890";
  private static LocalDate CLIENT_BIRTH_DAY = LocalDate.of(1990, 1, 22);

  @Test
  public void clientBirthdayLessThanReportRefusualDate() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID));

    List<CreditReportHistory> creditReportHistoryList =
        createValidCreditReportHistories(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getCreditReportHistories().addAll(creditReportHistoryList);

    checkRuleSatisfied(RULE_NAME);
  }

  private List<CreditReportHistory> createValidCreditReportHistories(LocalDate clientBirthDay,
      String childClientId) {
    return createCreditReportHistories(childClientId, clientBirthDay.plusYears(2));
  }

  @Test
  public void clientBirthdayGraterThanReportRefusualDate() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID));

    List<CreditReportHistory> creditReportHistoryList =
        createInvalidCreditReportHistories(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getCreditReportHistories().addAll(creditReportHistoryList);

    checkRuleViolatedOnce(RULE_NAME);
  }

  private List<CreditReportHistory> createInvalidCreditReportHistories(LocalDate clientBirthDay,
      String childClientId) {
    return createCreditReportHistories(childClientId, clientBirthDay.minusYears(2));
  }

  @Test
  public void clientBirthdayEqualToReportRefusualDate() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID));

    List<CreditReportHistory> creditReportHistoryList =
        createCreditReportHistoriesWithDateSameToBirthDate(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getCreditReportHistories().addAll(creditReportHistoryList);

    checkRuleSatisfied(RULE_NAME);
  }

  private List<CreditReportHistory> createCreditReportHistoriesWithDateSameToBirthDate(
      LocalDate clientBirthDay,
      String childClientId) {
    return createCreditReportHistories(childClientId, clientBirthDay);
  }

  @Test
  public void clientBirthdayIsNotPresentReport() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CHILD_CLIENT_ID));

    List<CreditReportHistory> creditReportHistoryList =
        createCreditReportHistoriesWithDateSameToBirthDate(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getCreditReportHistories().addAll(creditReportHistoryList);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void clientBirthdayAndReportRefusualDateAreNotAndPresent() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CHILD_CLIENT_ID));

    List<CreditReportHistory> creditReportHistoryList =
        createCreditReportHistories(CHILD_CLIENT_ID, null);
    clientEntityAwareDTO.getCreditReportHistories().addAll(creditReportHistoryList);

    checkRuleSatisfied(RULE_NAME);
  }

  private List<CreditReportHistory> createCreditReportHistories(
      String childClientId, LocalDate clientBirthDate) {
    CreditReportHistory creditReportHistory = new CreditReportHistory();
    creditReportHistory.setChildClientId(childClientId);
    creditReportHistory.setRequestRefusualDate(clientBirthDate);

    CreditReportHistory creditReportHistory2 = new CreditReportHistory();
    creditReportHistory2.setChildClientId(childClientId);
    creditReportHistory2.setRequestRefusualDate(clientBirthDate);

    return Arrays.asList(creditReportHistory, creditReportHistory2);
  }

  @Test
  public void refusualDateisNotAndPresent() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID));

    List<CreditReportHistory> creditReportHistoryList =
        createCreditReportHistories(CHILD_CLIENT_ID, null);
    clientEntityAwareDTO.getCreditReportHistories().addAll(creditReportHistoryList);

    checkRuleViolatedOnce(RULE_NAME);
  }
}
