package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import gov.ca.cwds.cms.data.access.mapper.ClientMapper;
import gov.ca.cwds.cms.data.access.service.DataAccessServicesException;
import gov.ca.cwds.cms.data.access.service.impl.dao.BaseClientDao;
import gov.ca.cwds.data.persistence.cms.BaseClientImpl;
import gov.ca.cwds.security.realm.PerryAccount;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.time.LocalDate;
import java.util.Date;
import gov.ca.cwds.data.persistence.cms.BaseClient;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.cms.data.access.service.BusinessValidationService;
import gov.ca.cwds.cms.data.access.service.impl.dbDependentSuite.BaseCwsCmsInMemoryPersistenceTest;
import gov.ca.cwds.data.legacy.cms.dao.ClientDao;
import gov.ca.cwds.data.legacy.cms.dao.ClientRelationshipDao;
import gov.ca.cwds.data.legacy.cms.dao.PaternityDetailDao;
import gov.ca.cwds.data.legacy.cms.dao.TribalMembershipVerificationDao;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.drools.DroolsService;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

/** @author CWDS TPT-3 Team */
@SuppressWarnings("Duplicates")
public class ClientRelationshipDBTest extends BaseCwsCmsInMemoryPersistenceTest {

  private static final String USER_ID = "0X5";

  public static final String EXISTING_CLIENT_ID_1 = "1111111111";
  public static final String EXISTING_CLIENT_ID_2 = "9999999999";
  public static final String EXISTING_RELATIONSHIP_ID = "9999999999";
  public static final String CLIENT_ID_1 = "1111111112";
  public static final String CLIENT_ID_2 = "1231231233";

  private ClientDao clientDao;
  private TribalMembershipVerificationDao tribalMembershipVerificationDao;
  private ClientRelationshipCoreService clientRelationshipCoreService;
  private ClientRelationshipDao clientRelationshipDao;
  private BusinessValidationService businessValidationService;
  private PaternityDetailDao paternityDetailDao;
  private UpdateLifeCycle updateLifeCycle;
  private CreateLifeCycle createLifeCycle;
  private SearchClientRelationshipService searchClientRelationshipService;
  private BaseClientDao baseClientDao;
  private ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

  @Before
  public void before() throws Exception {
    businessValidationService = new BusinessValidationService(new DroolsService());
    clientDao = new ClientDao(sessionFactory);
    tribalMembershipVerificationDao = new TribalMembershipVerificationDao(sessionFactory);
    clientRelationshipDao = new ClientRelationshipDao(sessionFactory);
    paternityDetailDao = new PaternityDetailDao(sessionFactory);
    searchClientRelationshipService = new SearchClientRelationshipService(clientRelationshipDao);
    updateLifeCycle =
        new UpdateLifeCycle(
            clientRelationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao,
            paternityDetailDao,
            searchClientRelationshipService);
    createLifeCycle =
        new CreateLifeCycle(
            clientRelationshipDao,
            businessValidationService,
            clientDao,
            tribalMembershipVerificationDao,
            paternityDetailDao,
            searchClientRelationshipService);
    clientRelationshipCoreService =
        new ClientRelationshipCoreService(
            clientRelationshipDao,
            updateLifeCycle,
            searchClientRelationshipService,
            createLifeCycle);

    baseClientDao = new BaseClientDao(sessionFactory);
    clientRelationshipCoreService.setClientMapper(clientMapper);
    cleanAllAndInsert("/dbunit/Relationships.xml");
    initUserAccount(USER_ID);
  }

  @Test
  public void createRelationshipTest() {

    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        List<ClientRelationship> relationships =
          clientRelationshipDao.findRelationshipsByPrimaryClientId(
            CLIENT_ID_2, LocalDate.now());
        assertNotNull(relationships);
        assertEquals(0, relationships.size());
      });

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Client client1 = clientDao.find(EXISTING_CLIENT_ID_1);
          Client client2 = clientDao.find(EXISTING_CLIENT_ID_2);

          sessionFactory.getCurrentSession().detach(client1);
          sessionFactory.getCurrentSession().detach(client2);

          client1.setIdentifier(CLIENT_ID_1);
          client2.setIdentifier(CLIENT_ID_2);

          BaseClientImpl baseClient1 = (BaseClientImpl) getBaseClient(client1);
          BaseClientImpl baseClient2 = (BaseClientImpl) getBaseClient(client2);
          baseClientDao.create(baseClient1);
          baseClientDao.create(baseClient2);

          ClientRelationship clientRelationship =
              clientRelationshipDao.find(EXISTING_RELATIONSHIP_ID);
          ClientRelationshipDTO dto = new ClientRelationshipDTO();
          dto.setPrimaryClient(baseClient1);
          dto.setSecondaryClient(baseClient2);
          dto.setType(clientRelationship.getType().getSystemId());
          dto.setStartDate(clientRelationship.getStartDate());
          dto.setEndDate(clientRelationship.getEndDate());
          dto.setSameHomeStatus(clientRelationship.getSameHomeStatus());
          dto.setAbsentParentIndicator(clientRelationship.getAbsentParentIndicator());

          try {
            clientRelationshipCoreService.createRelationship(dto);
          } catch (DataAccessServicesException e) {
            e.printStackTrace();
          }
        });

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          List<ClientRelationship> relationships =
            clientRelationshipDao.findRelationshipsByPrimaryClientId(
              CLIENT_ID_2, LocalDate.now());
          assertNotNull(relationships);
          assertEquals(1, relationships.size());
          assertNotNull(relationships.get(0).getPrimaryClient());
          assertEquals(CLIENT_ID_2, relationships.get(0).getPrimaryClient().getIdentifier());
          assertNotNull(relationships.get(0).getSecondaryClient());
          assertEquals(CLIENT_ID_1, relationships.get(0).getSecondaryClient().getIdentifier());
        });
  }

  private BaseClient getBaseClient(Client client) {
    BaseClient baseClient = new BaseClientImpl();
    baseClient.setAdjudicatedDelinquentIndicator("Y");
    baseClient.setAdoptionStatusCode(client.getAdoptionStatus().getCode());
    baseClient.setAlienRegistrationNumber(client.getAlienRegistrationNumber());
    baseClient.setBirthCity(client.getBirthCity());
    baseClient.setBirthCountryCodeType(client.getBirthCountryCode());
    baseClient.setBirthDate(new Date());
    baseClient.setBirthFacilityName(client.getBirthFacilityName());
    baseClient.setBirthStateCodeType(client.getBirthStateCode());
    baseClient.setBirthplaceVerifiedIndicator("Y");
    baseClient.setChildClientIndicatorVar("N");
    baseClient.setClientIndexNumber(client.getClientIndexNumber());
    baseClient.setCommentDescription(client.getCommentDescription());
    baseClient.setCommonFirstName(client.getCommonFirstName());
    baseClient.setCommonLastName(client.getCommonLastName());
    baseClient.setCommonMiddleName(client.getCommonMiddleName());
    baseClient.setConfidentialityActionDate(new Date());
    baseClient.setConfidentialityInEffectIndicator("Y");
    baseClient.setCreationDate(new Date());
    baseClient.setCurrCaChildrenServIndicator("Y");
    baseClient.setCurrentlyOtherDescription(client.getCurrentlyOtherDescription());
    baseClient.setCurrentlyRegionalCenterIndicator("N");
    baseClient.setDeathDate(new Date());
    baseClient.setDeathDateVerifiedIndicator("N");
    baseClient.setDeathPlace(client.getDeathPlace());
    baseClient.setDeathReasonText(client.getDeathReasonText());
    baseClient.setDriverLicenseNumber(client.getDriverLicenseNumber());
    baseClient.setDriverLicenseStateCodeType(client.getDriverLicenseStateCode());
    baseClient.setEmailAddress(client.getEmailAddress());
    baseClient.setEstimatedDobCode("");
    baseClient.setEthUnableToDetReasonCode("A");
    baseClient.setFatherParentalRightTermDate(new Date());
    baseClient.setGenderCode(client.getGender().getCode());
    baseClient.setHealthSummaryText("");
    baseClient.setHispanicOriginCode(client.getHispanicOrigin().getCode());
    baseClient.setId(client.getIdentifier());
    baseClient.setImmigrationCountryCodeType(client.getImmigrationCountryCode());
    baseClient.setImmigrationStatusType(client.getImmigrationStatusCode());
    baseClient.setIncapacitatedParentCode(client.getIncapacitatedParentStatus().getCode());
    baseClient.setIndividualHealthCarePlanIndicator("Y");
    baseClient.setLimitationOnScpHealthIndicator("N");
    baseClient.setLiterateCode(client.getLiterateStatus().getCode());
    baseClient.setMaritalCohabitatnHstryIndicatorVar("N");
    baseClient.setMaritalStatusType(client.getMaritalStatusCode());
    baseClient.setMilitaryStatusCode("D");
    baseClient.setMotherParentalRightTermDate(new Date());
    baseClient.setNamePrefixDescription("");
    baseClient.setNameType(client.getNameType().getSystemId());
    baseClient.setOutstandingWarrantIndicator("N");
    baseClient.setPrevCaChildrenServIndicator("N");
    baseClient.setPrevOtherDescription("");
    baseClient.setPrevRegionalCenterIndicator("Y");
    baseClient.setPrimaryEthnicityType(client.getPrimaryEthnicityCode());
    baseClient.setPrimaryLanguageType(client.getPrimaryLanguageCode());
    baseClient.setReligionType(client.getReligionCode());
    baseClient.setSecondaryLanguageType(client.getSecondaryLanguageCode());
    baseClient.setSensitiveHlthInfoOnFileIndicator("N");
    baseClient.setSensitivityIndicator("S");
    baseClient.setSoc158PlacementCode(client.getSoc158placementsStatus().getCode());
    baseClient.setSoc158SealedClientIndicator("Y");
    baseClient.setSocialSecurityNumChangedCode(client.getSocialSecurityNumberChangedCode());
    baseClient.setSocialSecurityNumber(client.getSocialSecurityNumber());
    baseClient.setSuffixTitleDescription("");
    baseClient.setTribalAncestryClientIndicatorVar("N");
    baseClient.setTribalMembrshpVerifctnIndicatorVar("N");
    baseClient.setUnemployedParentCode("N");
    baseClient.setZippyCreatedIndicator("N");
    baseClient.setLastUpdatedId("0X5");
    baseClient.setLastUpdatedTime(new Date());
    baseClient.setSexualOrientationType((short) 0);
    baseClient.setGenderIdentityType((short) 0);
    baseClient.setGenderExpressionType((short) 0);
    return baseClient;
  }

  private void initUserAccount(String userAccount) {
    PerryAccount perryAccount = new PerryAccount();
    mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getPrincipal()).thenReturn(perryAccount);
    when(PrincipalUtils.getStaffPersonId()).thenReturn(userAccount);
  }
}
