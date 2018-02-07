package gov.ca.cwds.cms.data.access.service.impl.client;

import gov.ca.cwds.cms.data.access.dto.ChildClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientPaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.HealthInterventionPlan;
import gov.ca.cwds.data.legacy.cms.entity.ParentalRightsTermination;
import gov.ca.cwds.data.legacy.cms.entity.PaternityDetail;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author CWDS TPT-3 Team
 */
public class ClientTestUtil {

  public static final String CLIENT_ID = "AdCfWS40FG";
  public static final String CHILD_CLIENT_ID = "ChIlDS40FG";
  public static final LocalDate SOME_DATE = LocalDate.of(2018, 1, 8);

  public static Client client() {
    Client client = new Client();
    client.setIdentifier(CLIENT_ID);
    return client;
  }

  public static Client client(LocalDate birthDate) {
    Client client = new Client();
    client.setIdentifier(CLIENT_ID);
    client.setBirthDate(birthDate);
    return client;
  }

  public static ChildClient childClient(LocalDate birthDate) {
    ChildClient childClient = new ChildClient();
    childClient.setIdentifier(CHILD_CLIENT_ID);
    childClient.setBirthDate(birthDate);
    return childClient;
  }

  public static HealthInterventionPlan plan(String thirdId, LocalDate startDate) {
    HealthInterventionPlan plan = new HealthInterventionPlan();
    plan.setThirdId(thirdId);
    plan.setStartDate(startDate);
    return plan;
  }

  public static SafetyAlert safetyAlert(String thirdId, LocalDate activationDate) {
    SafetyAlert safetyAlert = new SafetyAlert();
    safetyAlert.setThirdId(thirdId);
    safetyAlert.setActivationDate(activationDate);
    return safetyAlert;
  }

  public static ParentalRightsTermination termination(Client parent, LocalDate date) {
    ParentalRightsTermination termination = new ParentalRightsTermination();
    termination.setChild(childClient(SOME_DATE));
    termination.setParent(parent);
    termination.setDate(date);
    return termination;
  }

  public static PaternityDetail paternityDetail(String id, LocalDate date) {
    PaternityDetail paternityDetail = new ClientPaternityDetail();
    paternityDetail.setId(id);
    paternityDetail.setPaternityHearingDate(date);
    return paternityDetail;
  }

  public static ChildClientEntityAwareDTO dto(Client client) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(client);
    return dto;
  }

  public static ChildClientEntityAwareDTO dto(Client client, HealthInterventionPlan... plans) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(client);
    dto.getActiveHealthInterventionPlans().addAll(Arrays.asList(plans));
    return dto;
  }

  public static ChildClientEntityAwareDTO dto(Client client, SafetyAlert... safetyAlerts) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(client);
    dto.getSafetyAlerts().addAll(Arrays.asList(safetyAlerts));
    return dto;
  }

  public static ChildClientEntityAwareDTO dto(ChildClient childClient,
      ParentalRightsTermination... terminations) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(childClient);
    dto.getParentalRightsTerminations().addAll(Arrays.asList(terminations));
    return dto;
  }

  public static ChildClientEntityAwareDTO dto(ChildClient childClient,
      PaternityDetail... paternityDetails) {
    ChildClientEntityAwareDTO dto = new ChildClientEntityAwareDTO();
    dto.setEntity(childClient);
    dto.setPaternityDetails(Arrays.asList(paternityDetails));
    return dto;
  }

}
