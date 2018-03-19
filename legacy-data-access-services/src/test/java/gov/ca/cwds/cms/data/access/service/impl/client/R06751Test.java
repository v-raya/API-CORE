package gov.ca.cwds.cms.data.access.service.impl.client;

import static gov.ca.cwds.cms.data.access.service.impl.client.ClientTestUtil.SOME_DATE;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.Test;

public class R06751Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-06751";

  @Test
  public void testStartDateGtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(
        dto(client, clientServiceProvider(client, SOME_DATE.plusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testStartDateLtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleViolatedOnce(
        dto(client, clientServiceProvider(client, SOME_DATE.minusDays(1))),
        RULE_NAME);
  }

  @Test
  public void testTwoStartDatesLtBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleViolated(dto(client,
        clientServiceProvider(client, SOME_DATE.minusDays(1)),
        clientServiceProvider(client, SOME_DATE.minusDays(2))),
        RULE_NAME, 2);
  }

  @Test
  public void testStartDateEqBirthDate() throws Exception {
    Client client = client(SOME_DATE);
    checkRuleSatisfied(dto(client, clientServiceProvider(client, SOME_DATE)),
        RULE_NAME);
  }

  @Test
  public void testBirthDateNull() throws Exception {
    Client client = client(null);
    checkRuleSatisfied(dto(client, clientServiceProvider(client, SOME_DATE)),
        RULE_NAME);
  }

  public static ClientServiceProvider clientServiceProvider(Client parent, LocalDate startDate) {
    ClientServiceProvider clientServiceProvider = new ClientServiceProvider();
    clientServiceProvider.setClient(parent);

    clientServiceProvider.setStartDate(startDate);
    return clientServiceProvider;
  }

  public static Client client(LocalDate birthDate) {
    Client client = new Client();
    client.setIdentifier("ChIlDS40FG");
    client.setBirthDate(birthDate);
    return client;
  }

  public static ClientEntityAwareDTO dto(Client client, ClientServiceProvider... clientServiceProviders) {
    ClientEntityAwareDTO dto = new ClientEntityAwareDTO();
    dto.setEntity(client);
    dto.getClientServiceProviders().addAll(Arrays.asList(clientServiceProviders));
    return dto;
  }
}
