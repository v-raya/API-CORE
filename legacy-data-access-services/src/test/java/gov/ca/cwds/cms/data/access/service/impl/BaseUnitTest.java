package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientOtherEthnicity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author CWDS TPT-3 Team
 */
public abstract class BaseUnitTest {

  private static final DateTimeFormatter TIMESTAMP_FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss.SSSSSS");

  protected Client getPersistedClient(String clientId) {
    Client client = new Client();
    client.setIdentifier(clientId);
    addPersistedOtherEthnicity(client, "0000000002", (short) 2, "002",
        time("2002-11-01-12.53.07.580225"));
    addPersistedOtherEthnicity(client, "0000000003", (short) 3, "003",
        time("2004-02-12-14.56.37.492178"));
    return client;
  }

  private ClientOtherEthnicity addPersistedOtherEthnicity(
      Client client, String id, short code, String userId, LocalDateTime lastUpdateTime) {
    ClientOtherEthnicity coe = new ClientOtherEthnicity();
    coe.setId(id);
    coe.setEthnicityCode(code);
    coe.setLastUpdateId(userId);
    coe.setLastUpdateTime(lastUpdateTime);

    client.addOtherEthnicity(coe);
    return coe;
  }

  protected LocalDateTime time(String timestampStr) {
    return LocalDateTime.parse(timestampStr, TIMESTAMP_FORMATTER);
  }

}
