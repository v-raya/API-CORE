package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.temporal.ChronoUnit;

public class R02761Test extends BaseDocToolRulesChildClientImplementationTest {

  private static final String RULE_NAME = "R-02761";
  private static final String CLIENT_IDENTIFIER = "1234567890";
  private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.of(2014, 10, 10);

  @Before
  public void initClient() {
    ChildClient client = createClient();
    clientEntityAwareDTO.setEntity(client);
  }

  @Test
  public void allMedicalEligibilityApplicationValid() throws Exception {
    List<MedicalEligibilityApplication> medicalEligibilityApplications =
        generateListOfValidMedicalEligibilityApplications();
    clientEntityAwareDTO.getMedicalEligibilityApplications().addAll(medicalEligibilityApplications);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void oneMedicalEligibilityApplicationNotValid() throws Exception {
    List<MedicalEligibilityApplication> medicalEligibilityApplications =
        generateListOfValidMedicalEligibilityApplications();
    MedicalEligibilityApplication failedApplication = createMedicalEligibilityApplication(-2);
    medicalEligibilityApplications.add(failedApplication);
    clientEntityAwareDTO.getMedicalEligibilityApplications().addAll(medicalEligibilityApplications);

    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void birthDateNotSet() throws Exception {
    ChildClient client = new ChildClient();
    client.setIdentifier(CLIENT_IDENTIFIER);
    clientEntityAwareDTO.setEntity(client);

    List<MedicalEligibilityApplication> medicalEligibilityApplications =
        generateListOfValidMedicalEligibilityApplications();
    clientEntityAwareDTO.getMedicalEligibilityApplications().addAll(medicalEligibilityApplications);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void noMedicalEligibilityApplications() throws Exception {
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void medicalEligibilityApplicationSameLenghthAsAge() throws Exception {
    MedicalEligibilityApplication application = createMedicalEligibilityApplication(0);
    clientEntityAwareDTO.getMedicalEligibilityApplications().add(application);

    checkRuleSatisfied(RULE_NAME);
  }

  private static ChildClient createClient() {
    ChildClient client = new ChildClient();
    client.setBirthDate(DEFAULT_BIRTHDATE);
    client.setIdentifier(CLIENT_IDENTIFIER);
    return client;
  }

  private static List<MedicalEligibilityApplication>
      generateListOfValidMedicalEligibilityApplications() {
    List<MedicalEligibilityApplication> medicalEligibilityApplications = new ArrayList<>();
    MedicalEligibilityApplication medicalEligibilityApplication1 =
        createMedicalEligibilityApplication(3);
    MedicalEligibilityApplication medicalEligibilityApplication2 =
        createMedicalEligibilityApplication(2);
    MedicalEligibilityApplication medicalEligibilityApplication3 =
        createMedicalEligibilityApplication(5);
    medicalEligibilityApplications.addAll(
        Arrays.asList(
            medicalEligibilityApplication1,
            medicalEligibilityApplication2,
            medicalEligibilityApplication3));
    return medicalEligibilityApplications;
  }

  private static MedicalEligibilityApplication createMedicalEligibilityApplication(int adjustment) {
    short retroactiveMonthsCount = getAmountOfRetroactiveMonths(adjustment);
    MedicalEligibilityApplication medicalEligibilityApplication =
        new MedicalEligibilityApplication();
    medicalEligibilityApplication.setRetroactiveMonthsCount(retroactiveMonthsCount);
    return medicalEligibilityApplication;
  }

  private static short getAmountOfRetroactiveMonths(int adjustment) {
    return (short) (ChronoUnit.MONTHS.between(DEFAULT_BIRTHDATE, LocalDate.now()) - adjustment);
  }
}
