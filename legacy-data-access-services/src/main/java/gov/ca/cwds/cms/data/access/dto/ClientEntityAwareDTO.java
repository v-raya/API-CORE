package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.security.realm.PerryAccount;

public class ClientEntityAwareDTO extends BaseEntityAwareDTO<Client> {

  public ClientEntityAwareDTO(PerryAccount perryAccount) {
    super(perryAccount);
  }
}
