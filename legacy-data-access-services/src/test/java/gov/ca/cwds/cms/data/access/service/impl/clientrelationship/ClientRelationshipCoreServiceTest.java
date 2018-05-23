package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.BaseUnitTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

  @Test
  public void createRelationship() throws Exception {}

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

  @Before
  public void before() {
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(USER_ID);
    when(clientDao.find(PRIMARY_CLIENT)).thenReturn(getPersistedClient(PRIMARY_CLIENT));
    when(clientDao.find(SECONDARY_CLIENT)).thenReturn(getPersistedClient(SECONDARY_CLIENT));

    List<ClientRelationship> relationships = getListOfRelationships();

    searchClientRelationshipService = new SearchClientRelationshipService(relationshipDao);
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
            searchClientRelationshipService);
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
