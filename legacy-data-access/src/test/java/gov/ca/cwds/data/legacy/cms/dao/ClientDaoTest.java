package gov.ca.cwds.data.legacy.cms.dao;

import static gov.ca.cwds.data.legacy.cms.TestUtils.localDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.AdoptionStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.DateOfBirthStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.Gender;
import gov.ca.cwds.data.legacy.cms.entity.enums.HispanicOrigin;
import gov.ca.cwds.data.legacy.cms.entity.enums.IncapacitatedParentStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.LiterateStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.MilitaryStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.ParentUnemployedStatus;
import gov.ca.cwds.data.legacy.cms.entity.enums.Sensitivity;
import gov.ca.cwds.data.legacy.cms.entity.enums.Soc158placementsStatus;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.Country;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.State;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.time.LocalDateTime;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Test;

public class ClientDaoTest extends BaseCwsCmsInMemoryPersistenceTest {

  private ClientDao dao = null;
  private NameTypeDao nameTypeDao = null;

  @Before
  public void before() throws Exception {
    dao = new ClientDao(sessionFactory);
    nameTypeDao = new NameTypeDao(sessionFactory);
  }

  @Test
  public void testFind() throws Exception {
    cleanAllAndInsert("/dbunit/Clients.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          Client client_1 = dao.find("AaiU7IW0Rt");
          assertNotNull(client_1);
          assertFalse(client_1.getAdjudicatedDelinquentIndicator());
          assertNull(client_1.getHispanicUnableToDetermineReason());
          assertNull(client_1.getBirthState());
          assertNull(client_1.getBirthCountry());
          assertNull(client_1.getDriverLicenseState());
          assertNull(client_1.getImmigrationCountry());
          assertNull(client_1.getImmigrationStatus());
          assertNull(client_1.getReligion());
          assertNull(client_1.getSecondaryLanguage());

          Client client_2 = dao.find("AapJGAU04Z");
          assertNotNull(client_2);
          assertTrue(client_2 instanceof ChildClient);
          assertTrue(client_2.getAdjudicatedDelinquentIndicator());

          Client client_3 = dao.find("AasRx3r0Ha");
          assertNotNull(client_3);
          assertNull(client_3.getAdjudicatedDelinquentIndicator());

          Client client_4 = dao.find("AazXkWY06s");
          assertNotNull(client_4);
          assertNull(client_4.getAdjudicatedDelinquentIndicator());
        });
  }

  @Test
  public void testCreate() throws Exception {

    final String expectedFilePath = "/dbunit/Client_insert.xml";

    cleanAll(expectedFilePath);

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          NameType nameType = nameTypeDao.find(new Short((short) 1313));
          assertNotNull(nameType);
          assertEquals("Legal", nameType.getShortDescription().trim());
          assertEquals((short) 1313, nameType.getSystemId().shortValue());

          Client c = new Client();

          c.setIdentifier("AaiU7IW0Rt");
          c.setAdjudicatedDelinquentIndicator(false); // ADJDEL_IND
          c.setAdoptionStatus(AdoptionStatus.NOT_FREE); // ADPTN_STCD
          c.setAlienRegistrationNumber("AlienRegNum"); // ALN_REG_NO
          c.setBirthDate(localDate("1972-08-17")); // BIRTH_DT
          c.setBirthFacilityName("BirthFacilityName");

//          State birthState = new State();
//          birthState.setSystemId((short) 0);
//          c.setBirthState(birthState); // B_STATE_C
//          c.setBirthState(null);
          c.setBirthState(null);

//          Country birthCountry = new Country();
//          birthCountry.setSystemId((short) 0);
//          c.setBirthCountry(birthCountry);
          c.setBirthCountry(null); // B_CNTRY_C

          c.setBirthCity("Sacramento"); // BIRTH_CITY
          c.setChildClientIndicator(false); // CHLD_CLT_B
          c.setCommonFirstName("Tumbling"); // COM_FST_NM
          c.setCommonLastName("Lazenby"); // COM_LST_NM
          c.setCommonMiddleName("Midname"); // COM_MID_NM
          c.setConfidentialityInEffectIndicator(false); // CONF_EFIND
          c.setCreationDate(localDate("2004-08-17")); // CREATN_DT
          c.setDriverLicenseNumber("license"); // DRV_LIC_NO


          c.setGender(Gender.MALE); // GENDER_CD

          //          Country immigrationCountry = new Country();
          //          immigrationCountry.setSystemId((short)0);
          //          c.setImmigrationCountry(immigrationCountry); // I_CNTRY_C
          c.setImmigrationCountry(null); // I_CNTRY_C

          //          State driverLicenseState = new State();
          //          driverLicenseState.setSystemId((short)0);
          //          c.setDriverLicenseState(driverLicenseState);
          c.setDriverLicenseState(null); // D_STATE_C

          //          ImmigrationStatus immigrationStatus = new ImmigrationStatus();
          //          immigrationStatus.setSystemId((short)0);
          //          c.setImmigrationStatus(immigrationStatus); // IMGT_STC
          c.setImmigrationStatus(null); // IMGT_STC

          c.setIncapacitatedParentStatus(IncapacitatedParentStatus.UNKNOWN); // INCAPC_CD
          c.setLiterateStatus(LiterateStatus.UNKNOWN); // LITRATE_CD
          c.setMaritalCohabitationHistoryIndicator(false); // MAR_HIST_B

          //          MaritalStatus maritalStatus = new MaritalStatus();
          //          maritalStatus.setSystemId((short)0);
          //          c.setMaritalStatus(maritalStatus); // MRTL_STC
          c.setMaritalStatus(null); // MRTL_STC

          //          Ethnicity primaryEthnicity = new Ethnicity();
          //          primaryEthnicity.setSystemId((short)0);
          //          c.setPrimaryEthnicity(primaryEthnicity);
          c.setPrimaryEthnicity(null);//P_ETHNCTYC

          //          Language secondaryLanguage = new Language();
          //          secondaryLanguage.setSystemId((short)0);
          //          c.setSecondaryLanguage(secondaryLanguage);
          c.setSecondaryLanguage(null);//S_LANG_TC

          //          Language primaryLanguage = new Language();
          //          primaryLanguage.setSystemId((short)0);
          //          c.setPrimaryLanguage(primaryLanguage);
          c.setPrimaryLanguage(null);//P_LANG_TPC

          //          Religion religion = new Religion();
          //          religion.setSystemId((short)0);
          //          c.setReligion(religion);
          c.setReligion(null);//RLGN_TPC

          c.setMilitaryStatus(MilitaryStatus.NO_INFORMATION_AVAILABLE); // MILT_STACD
          c.setNamePrefixDescription("prefix"); // NMPRFX_DSC
          c.setNameType(nameType); // NAME_TPC
          c.setSensitivity(Sensitivity.NOT_APPLICABLE); // SENSTV_IND
          c.setSocialSecurityNumber("977000271"); // SS_NO
          c.setSocialSecurityNumberChangedCode("O"); // SSN_CHG_CD
          c.setSuffixTitleDescription("Jr."); // SUFX_TLDSC
          c.setParentUnemployedStatus(ParentUnemployedStatus.UNKNOWN); // UNEMPLY_CD
          c.setCommentDescription("CommentDescription"); // COMMNT_DSC
          c.setDateOfBirthStatus(DateOfBirthStatus.ESTIMATED); // EST_DOB_CD
          c.setHispanicOrigin(HispanicOrigin.UNDETERMINED); // HISP_CD
          c.setCurrentlyOtherDescription("CurrentlyOtherDescription"); // COTH_DESC
          c.setPreviousOtherDescription("PreviousOtherDescription");
          c.setSoc158placementsStatus(Soc158placementsStatus.NO_SOC_158_PLACEMENTS); // SOCPLC_CD
          c.setLastUpdateId("0Rt"); // LST_UPD_ID
          c.setLastUpdateTime(LocalDateTime.now()); // LST_UPD_TS

          dao.create(c);
        });

    IDataSet expectedDataSet = readXmlDataSet(expectedFilePath);
    ITable expectedTable = expectedDataSet.getTable("CLIENT_T");

    IDataSet actualDataSet = dbUnitConnection.createDataSet(new String[] {"CLIENT_T"});
    ITable actualTable = actualDataSet.getTable("CLIENT_T");

    assertTableEquals(expectedTable, actualTable);
  }

  @Test
  public void testUpdate() throws Exception {

    cleanAllAndInsert("/dbunit/Client_before_update.xml");

    executeInTransaction(
        sessionFactory,
        (sessionFactory) -> {
          NameType nameType = nameTypeDao.find(new Short((short) 1314));
          assertNotNull(nameType);
          assertEquals("Maiden", nameType.getShortDescription().trim());
          assertEquals((short) 1314, nameType.getSystemId().shortValue());

          Client c = dao.find("AaiU7IW0Rt");
          assertNull(c.getBirthState());

          c.setNameType(nameType); // NAME_TPC
          dao.update(c);
        });

    IDataSet expectedDataSet = readXmlDataSet("/dbunit/Client_after_update.xml");
    ITable expectedTable = expectedDataSet.getTable("CLIENT_T");

    IDataSet actualDataSet = dbUnitConnection.createDataSet(new String[] {"CLIENT_T"});
    ITable actualTable = actualDataSet.getTable("CLIENT_T");

    assertTableEquals(expectedTable, actualTable);
  }
}
