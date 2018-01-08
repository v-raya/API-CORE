package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.MedicalEligibilityApplication;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class R02127Test extends BaseDocToolRulesClientTest {

    private static final String RULE_NAME = "R-02127";
    private static final String CLIENT_IDENTIFIER = "1234567890";
    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.of(2014,10,10);



    @Test
    public void allApplicationDatesGtBirthdate() throws Exception {
        Client client = createClient();
        clientEntityAwareDTO.setEntity(client);

        List<MedicalEligibilityApplication> medicalEligibilityApplications = generateListOfValidMedicalEligibilityApplications();
        clientEntityAwareDTO.getMedicalEligibilityApplications().addAll(medicalEligibilityApplications);

        checkRuleSatisfied(RULE_NAME);
    }


    @Test
    public void oneApplicationDateLtBirthdate() throws Exception {
        Client client = createClient();
        clientEntityAwareDTO.setEntity(client);

        List<MedicalEligibilityApplication> medicalEligibilityApplications = generateListOfValidMedicalEligibilityApplications();
        MedicalEligibilityApplication failedApplication = createMedicalEligibilityApplication(2013, 5, 17);
        medicalEligibilityApplications.add(failedApplication);
        clientEntityAwareDTO.getMedicalEligibilityApplications().addAll(medicalEligibilityApplications);

        checkRuleViolatedOnce(RULE_NAME);
    }

    @Test
    public void birthDateNotSet() throws  Exception {
        Client client = new Client();
        client.setIdentifier(CLIENT_IDENTIFIER);
        clientEntityAwareDTO.setEntity(client);

        List<MedicalEligibilityApplication> medicalEligibilityApplications = generateListOfValidMedicalEligibilityApplications();
        clientEntityAwareDTO.getMedicalEligibilityApplications().addAll(medicalEligibilityApplications);

        checkRuleSatisfied(RULE_NAME);
    }

    @Test
    public void noApplications() throws Exception {
        Client client = createClient();
        clientEntityAwareDTO.setEntity(client);
        checkRuleSatisfied(RULE_NAME);
    }

    private static Client createClient() {
        Client client = new Client();
        client.setBirthDate(DEFAULT_BIRTHDATE);
        client.setIdentifier(CLIENT_IDENTIFIER);
        return client;
    }

    @Test
    public void adoptionAgreementTermDateSameAsBirthdate() throws Exception {
        Client client = createClient();
        clientEntityAwareDTO.setEntity(client);

        MedicalEligibilityApplication application = createMedicalEligibilityApplication(2014, 10, 10);
        clientEntityAwareDTO.getMedicalEligibilityApplications().add(application);

        checkRuleSatisfied(RULE_NAME);
    }


    private static List<MedicalEligibilityApplication> generateListOfValidMedicalEligibilityApplications() {
        List<MedicalEligibilityApplication> medicalEligibilityApplications = new ArrayList<>();
        MedicalEligibilityApplication medicalEligibilityApplication1 = createMedicalEligibilityApplication(2014, 12, 12);
        MedicalEligibilityApplication medicalEligibilityApplication2 = createMedicalEligibilityApplication(2017, 5, 25);
        MedicalEligibilityApplication medicalEligibilityApplication3 = createMedicalEligibilityApplication(2016, 3, 8);

        medicalEligibilityApplications.addAll(Arrays.asList(medicalEligibilityApplication1, medicalEligibilityApplication2, medicalEligibilityApplication3));
        return medicalEligibilityApplications;
    }


    private static MedicalEligibilityApplication createMedicalEligibilityApplication(int year, int month, int day) {
        MedicalEligibilityApplication medicalEligibilityApplication = new MedicalEligibilityApplication();
        medicalEligibilityApplication.setAdoptionAgreementTermDate(LocalDate.of(year, month, day));
        return medicalEligibilityApplication;
    }


}
