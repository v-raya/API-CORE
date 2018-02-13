package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.data.legacy.cms.entity.SpecialEducation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R09865Test extends BaseDocToolRulesChildClientImplementationTest {

  private static final String RULE_NAME = "R-09865";
  private static final String CHILD_CLIENT_ID = "1234567890";
  private static LocalDate CLIENT_BIRTH_DAY = LocalDate.of(1990, 1, 22);

  @Test
  public void birthDateLessThanStartEducationDate() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID));

    List<SpecialEducation> specialEducationList =
        createValidSpecialEducation(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getSpecialEducations().addAll(specialEducationList);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void birthDateGraterThanStartEducationDate() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID));

    List<SpecialEducation> specialEducationList =
        createInValidSpecialEducation(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getSpecialEducations().addAll(specialEducationList);

    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void birthDateAndStartEducationDatsAreEqual() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID));

    List<SpecialEducation> specialEducationList =
        createSpecialEducationsDateSameToBirthDate(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getSpecialEducations().addAll(specialEducationList);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void birthDateIsNotRepresent() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CHILD_CLIENT_ID));

    List<SpecialEducation> specialEducationList =
        createSpecialEducations(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getSpecialEducations().addAll(specialEducationList);

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void educationDateIsNotRepresent() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CLIENT_BIRTH_DAY, CHILD_CLIENT_ID));

    List<SpecialEducation> specialEducationList =
        createSpecialEducations(null, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getSpecialEducations().addAll(specialEducationList);

    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void educationDateAndBirthDateAreNotPresent() throws Exception {
    clientEntityAwareDTO.setEntity(ClientTestUtil.childClient(CHILD_CLIENT_ID));

    List<SpecialEducation> specialEducationList =
        createSpecialEducations(null, CHILD_CLIENT_ID);
    clientEntityAwareDTO.getSpecialEducations().addAll(specialEducationList);

    checkRuleSatisfied(RULE_NAME);
  }

  private List<SpecialEducation> createSpecialEducationsDateSameToBirthDate(
      LocalDate clientBirthDay,
      String childClientId) {
    return createSpecialEducations(clientBirthDay, childClientId);
  }

  private List<SpecialEducation> createValidSpecialEducation(LocalDate clientBirthDate,
      String childClientId) {
    return createSpecialEducations(clientBirthDate.plusYears(1), childClientId);
  }

  private List<SpecialEducation> createInValidSpecialEducation(LocalDate clientBirthDate,
      String childClientId) {
    return createSpecialEducations(clientBirthDate.minusYears(1), childClientId);
  }

  private List<SpecialEducation> createSpecialEducations(LocalDate startDate,
      String childClientId) {
    List<SpecialEducation> specialEducations = new ArrayList<>();

    SpecialEducation specialEducation1 = new SpecialEducation();
    specialEducation1.setStartDate(startDate);
    specialEducation1.setChildClientId(childClientId);
    specialEducations.add(specialEducation1);

    SpecialEducation specialEducation2 = new SpecialEducation();
    specialEducation2.setStartDate(startDate);
    specialEducation2.setChildClientId(childClientId);
    specialEducations.add(specialEducation2);

    return specialEducations;
  }
}
