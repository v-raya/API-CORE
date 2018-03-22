package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientOtherEthnicity;
import gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider;
import gov.ca.cwds.data.legacy.cms.entity.DasHistory;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientEntityAwareDTO extends BaseEntityAwareDTO<Client> {
  private Client persistentClientState;
  private Set<ClientOtherEthnicity> otherEthnicities = new HashSet<>();
  private List<DeliveredService> deliveredService;
  private final List<SafetyAlert> safetyAlerts = new ArrayList<>();
  private final List<DasHistory> dasHistories = new ArrayList<>();
  private List<NearFatality> nearFatalities = new ArrayList<>();
  private List<PlacementEpisode> placementEpisodes = new ArrayList<>();
  private List<ClientServiceProvider> clientServiceProviders = new ArrayList<>();
  private OtherClientNameDTO otherClientName;

  public Set<ClientOtherEthnicity> getOtherEthnicities() {
    return otherEthnicities;
  }

  public List<DeliveredService> getDeliveredService() {
    return deliveredService;
  }

  public void setDeliveredService(List<DeliveredService> deliveredService) {
    this.deliveredService = deliveredService;
  }

  public Client getPersistentClientState() {
    return persistentClientState;
  }

  public void setPersistentClientState(Client persistentClientState) {
    this.persistentClientState = persistentClientState;
  }

  public List<SafetyAlert> getSafetyAlerts() {
    return safetyAlerts;
  }

  public List<DasHistory> getDasHistories() {
    return dasHistories;
  }

  public List<NearFatality> getNearFatalities() {
    return nearFatalities;
  }

  public List<PlacementEpisode> getPlacementEpisodes() {
    return placementEpisodes;
  }

  public List<ClientServiceProvider> getClientServiceProviders() {
    return clientServiceProviders;
  }

  public OtherClientNameDTO getOtherClientName() {
    return otherClientName;
  }

  public void setOtherClientName(OtherClientNameDTO otherClientName) {
    this.otherClientName = otherClientName;
  }
}
