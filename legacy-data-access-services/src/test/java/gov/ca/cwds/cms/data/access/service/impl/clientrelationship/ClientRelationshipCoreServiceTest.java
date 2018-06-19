package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.cms.data.access.mapper.ClientMapper;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.BaseUnitTest;
import gov.ca.cwds.cms.data.access.service.impl.tribalmembership.TribalMembershipVerificationCoreService;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class ClientRelationshipCoreServiceTest extends BaseUnitTest {

  public static final String USER_ID = "aaa";
  private static final String PRIMARY_CLIENT = "aaddssrrtt";
  private static final String SECONDARY_CLIENT = "bbddssrrtt";

  private ClientRelationshipCoreService clientRelationshipCoreService;
  private UpdateLifeCycle updateLifeCycle;
  private CreateLifeCycle createLifeCycle;
  private SearchClientRelationshipService searchClientRelationshipService;
  @Mock private ClientRelationshipDao relationshipDao;
  @Mock private ClientDao clientDao;
  @Mock private BusinessValidationService businessValidationService;
  @Mock private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  @Mock private PaternityDetailDao paternityDetailDao;
  private TribalMembershipVerificationCoreService tribalMembershipVerificationCoreService;

  @Before
  public void before() {
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(USER_ID);
    when(clientDao.find(PRIMARY_CLIENT)).thenReturn(getPersistedClient(PRIMARY_CLIENT));
    when(clientDao.find(SECONDARY_CLIENT)).thenReturn(getPersistedClient(SECONDARY_CLIENT));

    List<ClientRelationship> relationships = getListOfRelationships();

    searchClientRelationshipService = new SearchClientRelationshipService(relationshipDao);
    tribalMembershipVerificationCoreService =
        new TribalMembershipVerificationCoreService(tribalMembershipVerificationDao, null, null);
    updateLifeCycle =
        new UpdateLifeCycle(
            relationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao,
            paternityDetailDao,
            searchClientRelationshipService);
    createLifeCycle =
        new CreateLifeCycle(
            relationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao,
            paternityDetailDao,
            searchClientRelationshipService,
            tribalMembershipVerificationCoreService);
    clientRelationshipCoreService =
        new ClientRelationshipCoreService(
            relationshipDao, updateLifeCycle, searchClientRelationshipService, createLifeCycle);

    when(relationshipDao.findRelationshipsByPrimaryClientId(anyString(), any(LocalDate.class)))
        .thenReturn(relationships);

    when(relationshipDao.findRelationshipsBySecondaryClientId(anyString(), any(LocalDate.class)))
        .thenReturn(relationships);
  }

  @Test
  public void findRelationshipsBySecondaryClientId() throws Exception {
    List<ClientRelationship> relationships =
        clientRelationshipCoreService.findRelationshipsBySecondaryClientId(SECONDARY_CLIENT);
    assertEquals(2, relationships.size());

    relationships.forEach(e -> testRelationship(e));
  }

  @Test
  public void findRelationshipsByPrimaryClientId() throws Exception {
    List<ClientRelationship> relationships =
        clientRelationshipCoreService.findRelationshipsByPrimaryClientId(PRIMARY_CLIENT);
    assertEquals(2, relationships.size());

    relationships.forEach(e -> testRelationship(e));
  }

  @Test
  public void createRelationship() throws DataAccessServicesException {
    Client primaryClientEntity = new Client();
    primaryClientEntity.setIdentifier("primaryId");
    Client secondaryClientEntity = new Client();
    primaryClientEntity.setIdentifier("secondaryId");

    SessionFactory factory = mock(SessionFactory.class);
    when(relationshipDao.getSessionFactory()).thenReturn(factory);
    ClientRelationship savedRelationship = mock(ClientRelationship.class);
    when(relationshipDao.create(any(ClientRelationship.class))).thenReturn(savedRelationship);
    when(clientDao.getSessionFactory()).thenReturn(factory);
    Session session = mock(Session.class);
    when(factory.getCurrentSession()).thenReturn(session);
    when(session.load(Client.class, primaryClientEntity.getIdentifier())).thenReturn(primaryClientEntity);
    when(session.load(Client.class, secondaryClientEntity.getIdentifier())).thenReturn(secondaryClientEntity);

    ClientRelationshipDTO relationship = new ClientRelationshipDTO();
    BaseClient primaryClient = mock(BaseClient.class);
    BaseClient secondaryClient = mock(BaseClient.class);
    relationship.setPrimaryClient(primaryClient);
    relationship.setSecondaryClient(secondaryClient);

    ClientRelationshipCoreService service = new ClientRelationshipCoreService( relationshipDao,
    mock(UpdateLifeCycle.class), searchClientRelationshipService, mock(CreateLifeCycle.class));
    ClientMapper mapper = mock(ClientMapper.class);
    when(mapper.toLegacyClient(primaryClient)).thenReturn(primaryClientEntity);
    when(mapper.toLegacyClient(secondaryClient)).thenReturn( secondaryClientEntity);
    service.setClientMapper(mapper);

    ClientRelationship actualRelationship = service.createRelationship(relationship);

    assertEquals(savedRelationship, actualRelationship);
  }

  private void testRelationship(ClientRelationship e) {
    assertNotNull(e);
    assertNotNull(e.getPrimaryClient());
    assertEquals(PRIMARY_CLIENT, e.getPrimaryClient().getIdentifier());

    assertNotNull(e);
    assertNotNull(e.getSecondaryClient());
    assertEquals(SECONDARY_CLIENT, e.getSecondaryClient().getIdentifier());
  }

  private List<ClientRelationship> getListOfRelationships() {
    List<ClientRelationship> relationships = new ArrayList<>();
    relationships.add(getRelationship(SECONDARY_CLIENT));
    relationships.add(getRelationship(SECONDARY_CLIENT));
    return relationships;
  }

  private ClientRelationship getRelationship(String secondaryClientId) {
    ClientRelationship clientRelationship = new ClientRelationship();
    clientRelationship.setIdentifier("1122334455");

    Client primaryClient = new Client();
    primaryClient.setIdentifier(PRIMARY_CLIENT);
    clientRelationship.setPrimaryClient(primaryClient);

    Client secondaryClient = new Client();
    secondaryClient.setIdentifier(secondaryClientId);
    clientRelationship.setSecondaryClient(secondaryClient);

    return clientRelationship;
  }
}
