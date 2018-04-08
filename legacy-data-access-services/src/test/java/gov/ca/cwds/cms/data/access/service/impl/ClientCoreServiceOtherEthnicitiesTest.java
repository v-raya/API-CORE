package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessBundle;
import gov.ca.cwds.cms.data.access.service.lifecycle.DataAccessServiceLifecycle;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientOtherEthnicityDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientOtherEthnicity;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PrincipalUtils.class)
public class ClientCoreServiceOtherEthnicitiesTest extends BaseUnitTest {

  private static final String CLIENT_ID = "0000000001";

  private static final String USER_ID = "0X5";

  @Mock private ClientDao clientDao;
  @Mock private ClientOtherEthnicityDao clientOtherEthnicityDao;

  private ClientCoreService clientCoreService;

  @Before
  public void before() {
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(USER_ID);

    when(clientDao.find(CLIENT_ID)).thenReturn(getPersistedClient(CLIENT_ID));
    clientCoreService = new ClientCoreService(clientDao);
    clientCoreService.setClientOtherEthnicityDao(clientOtherEthnicityDao);
  }

  @Test
  public void testAfterBusinessValidation() {
    Client client = getClient();
    ClientEntityAwareDTO clientEntityAwareDTO = new ClientEntityAwareDTO();
    clientEntityAwareDTO.setEntity(client);

    DataAccessBundle<ClientEntityAwareDTO> bundle = new DataAccessBundle(clientEntityAwareDTO);

    clientCoreService.getUpdateLifeCycle().afterBusinessValidation(bundle);

    // assert that ClientOtherEthnicity with code 3 will be removed
    verify(clientOtherEthnicityDao).delete(eq("0000000003"));

    Set<ClientOtherEthnicity> otherEtnicities = client.getOtherEthnicities();
    assertEquals(2, otherEtnicities.size());

    Map<Short, ClientOtherEthnicity> etnicitiesmap =
        otherEtnicities
            .stream()
            .collect(Collectors.toMap(ClientOtherEthnicity::getEthnicityCode, Function.identity()));

    Set<Short> ethnicityCodes = etnicitiesmap.keySet();
    assertTrue(ethnicityCodes.contains((short) 1));
    assertTrue(ethnicityCodes.contains((short) 2));

    // ClientOtherEthnicity for insert
    ClientOtherEthnicity coe1 = etnicitiesmap.get((short) 1);
    assertNotNull(coe1.getId());
    assertNotNull(coe1.getLastUpdateTime());
    assertEquals(USER_ID, coe1.getLastUpdateId());

    // ClientOtherEthnicity for update, the same state as in DB
    ClientOtherEthnicity coe2 = etnicitiesmap.get((short) 2);
    assertEquals("0000000002", coe2.getId());
    assertEquals(time("2002-11-01-12.53.07.580225"), coe2.getLastUpdateTime());
    assertEquals("002", coe2.getLastUpdateId());
  }

  private Client getClient() {
    Client client = new Client();
    client.setIdentifier(CLIENT_ID);
    addOtherEthnicity(client, (short) 1);
    addOtherEthnicity(client, (short) 2);
    return client;
  }

  private ClientOtherEthnicity addOtherEthnicity(Client client, short code) {
    ClientOtherEthnicity coe = new ClientOtherEthnicity();
    coe.setEthnicityCode(code);
    client.addOtherEthnicity(coe);
    return coe;
  }
}
