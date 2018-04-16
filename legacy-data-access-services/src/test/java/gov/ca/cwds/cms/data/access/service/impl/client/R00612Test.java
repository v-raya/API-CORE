package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.enums.IndividualType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R00612Test extends BaseDocToolRulesClientImplementationTest {

  private static final String RULE_NAME = "R-00612";
  private static final String CLIENT_ID = "1234567891";
  private static final String WRONG_CLIENT_ID = "2222222222";
  private static LocalDate CLIENT_BIRTH_DAY = LocalDate.of(2000, 10, 11);

  @Test
  public void testValidClientAndDelivery() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<DeliveredService> deliveredServices = new ArrayList<>();
    deliveredServices.add(getDeliveredService());

    clientEntityAwareDTO.getDeliveredService().addAll(deliveredServices);
    checkRuleSatisfied(RULE_NAME);
  }

  private DeliveredService getDeliveredService() {
    return getDeliveredService(CLIENT_BIRTH_DAY.plusMonths(10), CLIENT_ID, IndividualType.CLIENTS);
  }

  @Test
  public void testValidClientAndNoDelivery() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<DeliveredService> deliveredServices = new ArrayList<>();
    deliveredServices.add(
        getDeliveredService(
            CLIENT_BIRTH_DAY.plusMonths(10), WRONG_CLIENT_ID, IndividualType.CLIENTS));

    clientEntityAwareDTO.getDeliveredService().addAll(deliveredServices);
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testValidClientValidDeliveryWithSameDates() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<DeliveredService> deliveredServices = new ArrayList<>();
    deliveredServices.add(getDeliveredService(CLIENT_BIRTH_DAY, CLIENT_ID, IndividualType.CLIENTS));

    clientEntityAwareDTO.getDeliveredService().addAll(deliveredServices);
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testInvalidClientDate() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY.plusDays(10)));

    List<DeliveredService> deliveredServices = new ArrayList<>();
    deliveredServices.add(
        getDeliveredService(CLIENT_BIRTH_DAY.minusYears(1), CLIENT_ID, IndividualType.CLIENTS));

    clientEntityAwareDTO.getDeliveredService().addAll(deliveredServices);
    checkRuleViolatedOnce(RULE_NAME);
  }

  @Test
  public void testInvalidIndividualTypeWithValidDates() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<DeliveredService> deliveredServices = new ArrayList<>();
    deliveredServices.add(
        getDeliveredService(CLIENT_BIRTH_DAY.plusMonths(10), CLIENT_ID, IndividualType.ATTORNEYS));

    clientEntityAwareDTO.getDeliveredService().addAll(deliveredServices);
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testInvalidIndividualTypeWithInvalidDates() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY.plusDays(10)));

    List<DeliveredService> deliveredServices = new ArrayList<>();
    deliveredServices.add(
        getDeliveredService(
            CLIENT_BIRTH_DAY.minusYears(1), CLIENT_ID, IndividualType.SERVICE_PROVIDERS));

    clientEntityAwareDTO.getDeliveredService().addAll(deliveredServices);
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testValidDeliveredServices() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<DeliveredService> deliveredServices = new ArrayList<>();
    deliveredServices.add(getDeliveredService());
    deliveredServices.add(
        getDeliveredService()); // I've added 2 lines because I've found 2 same lines in DB
    deliveredServices.add(
        getDeliveredService(CLIENT_BIRTH_DAY.plusMonths(10), CLIENT_ID, IndividualType.ATTORNEYS));

    clientEntityAwareDTO.getDeliveredService().addAll(deliveredServices);
    checkRuleSatisfied(RULE_NAME);
  }

  @Test
  public void testValinClientAndValidAndInvalidDeliveredService() throws Exception {
    clientEntityAwareDTO.setEntity(createClient(CLIENT_BIRTH_DAY));

    List<DeliveredService> deliveredServices = new ArrayList<>();
    deliveredServices.add(getDeliveredService());
    deliveredServices.add(
        getDeliveredService(CLIENT_BIRTH_DAY.minusYears(2), CLIENT_ID, IndividualType.CLIENTS));

    clientEntityAwareDTO.getDeliveredService().addAll(deliveredServices);
    checkRuleViolatedOnce(RULE_NAME);
  }

  private DeliveredService getDeliveredService(
      LocalDate startDate, String clientId, IndividualType individualType) {
    DeliveredService deliveredService = new DeliveredService();
    deliveredService.setIndividualType(individualType);
    deliveredService.setIndividualId(clientId);
    deliveredService.setStartDate(startDate);

    return deliveredService;
  }

  private ChildClient createClient(LocalDate birthDay) {
    ChildClient client = new ChildClient();
    client.setIdentifier(CLIENT_ID);
    client.setBirthDate(birthDay);

    return client;
  }
}
