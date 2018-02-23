package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R09619Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-09619";

  @Test
  public void testFatalityDateGreaterBirthDateAndLessThanDeceasedDate() throws Exception {
    LocalDateTime fatalityDate = LocalDateTime.of(2010, 10, 9, 0, 0);
    LocalDate clientBirthDate = LocalDate.of(1990, 10, 9);
    LocalDate clientDeathDate = LocalDate.of(2015, 10, 9);

    clientEntityAwareDTO
        .getNearFatalities()
        .addAll(
            getFatalitiesList(fatalityDate));
    clientEntityAwareDTO.setEntity(getClient(clientBirthDate, clientDeathDate));

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testFatalityDateGreaterBirthDateAndEqualDeceasedDate() throws Exception {
    LocalDateTime fatalityDate = LocalDateTime.of(1988, 10, 9, 0, 0);
    LocalDate clientBirthDate = LocalDate.of(1990, 10, 9);
    LocalDate clientDeathDate = LocalDate.of(2010, 9, 9);//fatalityDate.toLocalDate();

    clientEntityAwareDTO
        .getNearFatalities()
        .addAll(
            getFatalitiesList(fatalityDate));
    clientEntityAwareDTO.setEntity(getClient(clientBirthDate, clientDeathDate));
    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void testFatalityDateGreaterBirthDateAndGreaterThanDeceasedDate() throws Exception {
    LocalDateTime fatalityDate = LocalDateTime.of(2010, 10, 9, 0, 0);
    LocalDate clientBirthDate = LocalDate.of(2000, 10, 9);
    LocalDate clientDeathDate = LocalDate.of(2008, 10, 9);

    clientEntityAwareDTO
        .getNearFatalities()
        .addAll(
            getFatalitiesList(fatalityDate));
    clientEntityAwareDTO.setEntity(getClient(clientBirthDate, clientDeathDate));

    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void testFatalityDateEqualBirthDateAndLessThanDeceasedDate() throws Exception {
    LocalDateTime fatalityDate = LocalDateTime.of(2010, 10, 9, 0, 0);
    LocalDate clientBirthDate = LocalDate.of(2010, 10, 9);
    LocalDate clientDeathDate = LocalDate.of(2012, 10, 9);

    clientEntityAwareDTO
        .getNearFatalities()
        .addAll(
            getFatalitiesList(fatalityDate));
    clientEntityAwareDTO.setEntity(getClient(clientBirthDate, clientDeathDate));

    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testFatalityDateEqualBirthDateAndEqualDeceasedDate() throws Exception {
    LocalDateTime fatalityDate = LocalDateTime.of(2010, 10, 9, 0, 0);
    LocalDate clientBirthDate = fatalityDate.toLocalDate();
    LocalDate clientDeathDate = fatalityDate.toLocalDate();

    clientEntityAwareDTO
        .getNearFatalities()
        .addAll(
            getFatalitiesList(fatalityDate));
    clientEntityAwareDTO.setEntity(getClient(clientBirthDate, clientDeathDate));

    checkRuleViolatedOnce(clientEntityAwareDTO, RULE_NAME);
  }


  @Test
  public void testFatalityDateLessThanBirthDateAndLessThanDeceasedDate() throws Exception {
    LocalDateTime fatalityDate = LocalDateTime.of(2010, 10, 9, 0, 0);
    LocalDate clientBirthDate = LocalDate.of(2011, 10, 9);
    LocalDate clientDeathDate = LocalDate.of(2011, 11, 9);

    clientEntityAwareDTO
        .getNearFatalities()
        .addAll(
            getFatalitiesList(fatalityDate));
    clientEntityAwareDTO.setEntity(getClient(clientBirthDate, clientDeathDate));

    checkRuleViolatedOnce(clientEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void testFatalityDateLessThanBirthDateAndEqualDeceasedDate() throws Exception {
    LocalDateTime fatalityDate = LocalDateTime.of(2010, 10, 9, 0, 0);
    LocalDate clientBirthDate = LocalDate.of(2011, 10, 9);
    LocalDate clientDeathDate = fatalityDate.toLocalDate();

    clientEntityAwareDTO
        .getNearFatalities()
        .addAll(
            getFatalitiesList(fatalityDate));
    clientEntityAwareDTO.setEntity(getClient(clientBirthDate, clientDeathDate));

    checkRuleViolatedOnce(clientEntityAwareDTO, RULE_NAME);
  }

  @Test
  public void testFatalityDateLessThanBirthDateAndGreaterThanDeceasedDate() throws Exception {
    LocalDateTime fatalityDate = LocalDateTime.of(2010, 10, 9, 0, 0);
    LocalDate clientBirthDate = LocalDate.of(2011, 10, 9);
    LocalDate clientDeathDate = LocalDate.of(2010, 9, 9);

    clientEntityAwareDTO
        .getNearFatalities()
        .addAll(
            getFatalitiesList(fatalityDate));
    clientEntityAwareDTO.setEntity(getClient(clientBirthDate, clientDeathDate));

    checkRuleViolatedOnce(clientEntityAwareDTO, RULE_NAME);
  }

  private Client getClient(LocalDate clientBirthDay, LocalDate deathDate) {
    Client client = new Client();
    client.setBirthDate(clientBirthDay);
    client.setDeathDate(deathDate);
    return client;
  }

  private List<NearFatality> getFatalitiesList(LocalDateTime fatalitiesDate) {
    List<NearFatality> fatalities = new ArrayList<>();

    NearFatality fatality1 = new NearFatality();
    fatality1.setFatalityDate(fatalitiesDate);

    NearFatality fatality2 = new NearFatality();
    fatality2.setFatalityDate(fatalitiesDate);

    fatalities.add(fatality1);
    fatalities.add(fatality2);
    return fatalities;
  }
}
