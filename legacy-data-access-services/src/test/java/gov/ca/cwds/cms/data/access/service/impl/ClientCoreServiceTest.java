package gov.ca.cwds.cms.data.access.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gov.ca.cwds.cms.data.access.dto.ClientEntityAwareDTO;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/** @author CWDS TPT-3 Team */
@RunWith(PowerMockRunner.class)
@PrepareForTest(PrincipalUtils.class)
public class ClientCoreServiceTest extends BaseUnitTest {

  public static final String USER_ID = "aaa";
  public static final String CLIENT_CREATED_ID = "0000111123";
  public static final String CLIENT_FIND_ID = "00112233dd";

  @Mock private ClientDao clientDao;

  private ClientCoreService clientCoreService;

  private static final Function<String, Client> clientFunction =
      (a) -> {
        Client c = new Client();
        c.setIdentifier(a);
        return c;
      };

  private static final Function<Client, ClientEntityAwareDTO> awareDTOFunction =
      (a) -> {
        ClientEntityAwareDTO awareDTO = new ClientEntityAwareDTO();
        awareDTO.setEntity(a);
        return awareDTO;
      };

  private static final Function<String, Stream<Client>> streamFunction = (a) -> {
    List<Client> clients = Arrays.asList(clientFunction.apply(CLIENT_FIND_ID));
    return StreamSupport.stream(clients.spliterator(), false);
  };

  @Before
  public void before() {
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(USER_ID);
    when(clientDao.create(any(Client.class))).thenReturn(clientFunction.apply(CLIENT_CREATED_ID));
    when(clientDao.find(any(String.class))).thenReturn(clientFunction.apply(CLIENT_FIND_ID));
    when(clientDao.findByLicNumAndChildId(any(String.class), any(String.class))).thenReturn(clientFunction.apply(CLIENT_FIND_ID));
    when(clientDao.findByFacilityIdAndChildId(any(String.class), any(String.class))).thenReturn(clientFunction.apply(CLIENT_FIND_ID));
    when(clientDao.streamByLicenseNumber(any(String.class))).thenReturn(streamFunction.apply(CLIENT_FIND_ID));
    when(clientDao.streamByFacilityId(any(String.class))).thenReturn(streamFunction.apply(CLIENT_FIND_ID));
    clientCoreService = new ClientCoreService(clientDao);
  }

  @Test
  public void create() throws Exception {
    Client client =
        clientCoreService.create(awareDTOFunction.apply(clientFunction.apply(CLIENT_CREATED_ID)));
    assertEquals(CLIENT_CREATED_ID, client.getIdentifier());
  }

  @Test
  public void find() throws Exception {
    Client foundClient = clientCoreService.find(CLIENT_FIND_ID);
    assertNotNull(foundClient);
    assertEquals(CLIENT_FIND_ID, foundClient.getIdentifier());
  }

  @Test
  public void getClientByLicNumAndChildId() throws Exception {
    Client foundClient = clientCoreService.getClientByLicNumAndChildId("licNumber", CLIENT_FIND_ID);
    assertNotNull(foundClient);
    assertEquals(CLIENT_FIND_ID, foundClient.getIdentifier());
  }

  @Test
  public void getClientByFacilityIdAndChildId() throws Exception {
    Client foundClient = clientCoreService.getClientByFacilityIdAndChildId("licNumber", CLIENT_FIND_ID);
    assertNotNull(foundClient);
    assertEquals(CLIENT_FIND_ID, foundClient.getIdentifier());
  }

  @Test
  public void getClientsByLicenseNumber() throws Exception {
    List<Client> foundClient = clientCoreService.getClientsByLicenseNumber("licNumber");
    assertNotNull(foundClient);
    assertEquals(1, foundClient.size());
    assertEquals(CLIENT_FIND_ID, foundClient.get(0).getIdentifier());
  }

  @Test
  public void streamByFacilityId() throws Exception {
    List<Client> clientStream = clientCoreService.streamByFacilityId("facilityId");
    assertNotNull(clientStream);
    assertEquals(1, clientStream.size());
    assertEquals(CLIENT_FIND_ID, clientStream.get(0).getIdentifier());
  }

  @Test
  public void getUpdateLifeCycle() throws Exception {}

  @Test
  public void getCreateLifeCycle() throws Exception {}

  @Test
  public void getDeleteLifeCycle() throws Exception {}
}
