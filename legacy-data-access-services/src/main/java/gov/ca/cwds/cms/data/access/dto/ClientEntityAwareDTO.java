package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider;
import gov.ca.cwds.data.legacy.cms.entity.DasHistory;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import gov.ca.cwds.data.legacy.cms.entity.PlacementEpisode;
import gov.ca.cwds.data.legacy.cms.entity.SafetyAlert;

import java.util.ArrayList;
import java.util.List;

public class ClientEntityAwareDTO extends BaseEntityAwareDTO<Client> {

  private Client persistentClientState;
  private List<DeliveredService> deliveredService = new ArrayList<>();
  private final List<SafetyAlert> safetyAlerts = new ArrayList<>();
  private final List<DasHistory> dasHistories = new ArrayList<>();
  private List<NearFatality> nearFatalities = new ArrayList<>();
  private List<PlacementEpisode> placementEpisodes = new ArrayList<>();
  private List<ClientServiceProvider> clientServiceProviders = new ArrayList<>();
  private OtherClientNameDTO otherClientName;
  private List<ClientRelationship> clientRelationships = new ArrayList<>();
  private boolean updateClientPhoneticName;
  private boolean isEnriched;

  public List<DeliveredService> getDeliveredService() {
    return deliveredService;
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

  public List<ClientRelationship> getClientRelationships() {
    return clientRelationships;
  }

  public boolean isUpdateClientPhoneticName() {
    return updateClientPhoneticName;
  }

  public void setUpdateClientPhoneticName(boolean updateClientPhoneticName) {
    this.updateClientPhoneticName = updateClientPhoneticName;
  }

  public boolean isEnriched() {
    return isEnriched;
  }

  public void setEnriched(boolean enriched) {
    isEnriched = enriched;
  }
}
