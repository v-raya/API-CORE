package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.dao.ClientAddressDao;
import gov.ca.cwds.data.legacy.cms.entity.ClientAddress;
import gov.ca.cwds.security.annotations.Authorize;

import javax.inject.Inject;
import java.util.Collection;

import static gov.ca.cwds.cms.data.access.Constants.Authorize.CLIENT_READ_CLIENT_ID;

/** @author CWDS TPT-3 Team */
public class ClientAddressService {

  private final ClientAddressDao clientAddressDao;

  @Inject
  public ClientAddressService(final ClientAddressDao clientAddressDao) {
    this.clientAddressDao = clientAddressDao;
  }

  public Collection<ClientAddress> findByClientId(
      @Authorize(CLIENT_READ_CLIENT_ID) final String clientId) {
    return clientAddressDao.findByClientId(clientId);
  }
}
