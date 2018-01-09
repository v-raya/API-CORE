package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientScpEthnicity;

import java.util.ArrayList;
import java.util.List;

public class ClientEntityAwareDTO extends BaseEntityAwareDTO<Client> {
    private List<ClientScpEthnicity> clientScpEthnicities = new ArrayList<>();

    public List<ClientScpEthnicity> getClientScpEthnicities() {
        return clientScpEthnicities;
    }

    public void setClientScpEthnicities(List<ClientScpEthnicity> clientScpEthnicities) {
        this.clientScpEthnicities = clientScpEthnicities;
    }
}
