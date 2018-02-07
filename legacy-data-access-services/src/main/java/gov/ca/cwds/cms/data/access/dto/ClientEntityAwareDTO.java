package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;
import gov.ca.cwds.data.legacy.cms.entity.DeliveredService;
import gov.ca.cwds.data.legacy.cms.entity.NearFatality;
import java.util.ArrayList;
import java.util.List;

public class ClientEntityAwareDTO extends BaseEntityAwareDTO<Client> {
  private Client persistentClientState;
  private List<ClientScpEthnicity> clientScpEthnicities = new ArrayList<>();
  private List<DeliveredService> deliveredService;
  private List<NearFatality> nearFatalities = new ArrayList<>();

  public List<ClientScpEthnicity> getClientScpEthnicities() {
    return clientScpEthnicities;
  }

  public void setClientScpEthnicities(List<ClientScpEthnicity> clientScpEthnicities) {
    this.clientScpEthnicities = clientScpEthnicities;
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

  public List<NearFatality> getNearFatalities() {
    return nearFatalities;
  }
}
