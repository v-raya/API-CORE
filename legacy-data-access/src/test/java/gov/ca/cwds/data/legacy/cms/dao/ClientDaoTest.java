package gov.ca.cwds.data.legacy.cms.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import gov.ca.cwds.data.legacy.cms.entity.ChildClient;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import gov.ca.cwds.data.legacy.cms.entity.enums.AccessType;
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
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import gov.ca.cwds.data.legacy.cms.persistence.BaseCwsCmsInMemoryPersistenceTest;
import java.time.LocalDate;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.Assert;
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
    cleanAllAndInsert("/dbunit/Client/find/Clients.xml");

    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        Client client_1 = dao.find("AaiU7IW0Rt");
        assertNotNull(client_1);
        assertFalse(client_1.getAdjudicatedDelinquentIndicator());
        assertNull(client_1.getHispanicUnableToDetermineReason());

        assertTrue(client_1.getBirthStateCode() == 0);
        assertTrue(client_1.getBirthCountryCode() == 0);
        assertTrue(client_1.getDriverLicenseStateCode() == 0);
        assertTrue(client_1.getImmigrationCountryCode() == 0);
        assertTrue(client_1.getImmigrationStatusCode() == 0);
        assertTrue(client_1.getReligionCode() == 0);
        assertTrue(client_1.getPrimaryLanguageCode() == 0);
        assertTrue(client_1.getSecondaryLanguageCode() == 0);
        assertTrue(client_1.getMaritalStatusCode() == 0);
        assertTrue(client_1.getPrimaryEthnicityCode() == 0);

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

    final String expectedFilePath = "/dbunit/Client/insert/Client_after_insert.xml";

    cleanAll(expectedFilePath);
    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        NameType nameType = nameTypeDao.find((short) 1313);
        assertNotNull(nameType);
        assertEquals("Legal", nameType.getShortDescription().trim());
        assertTrue(nameType.getSystemId().equals(new Short((short) 1313)));

        Client c = new Client();

        c.setIdentifier("AaiU7IW0Rt");
        c.setAdjudicatedDelinquentIndicator(false); // ADJDEL_IND
        c.setAdoptionStatus(AdoptionStatus.NOT_FREE); // ADPTN_STCD
        c.setAlienRegistrationNumber("AlienRegNum"); // ALN_REG_NO
        c.setBirthDate(LocalDate.of(1972, 8, 17)); // BIRTH_DT
        c.setBirthFacilityName("BirthFacilityName");
        c.setBirthCity("Sacramento"); // BIRTH_CITY
        c.setChildClientIndicator(false); // CHLD_CLT_B
        c.setCommonFirstName("Tumbling"); // COM_FST_NM
        c.setCommonLastName("Lazenby"); // COM_LST_NM
        c.setCommonMiddleName("Midname"); // COM_MID_NM
        c.setConfidentialityInEffectIndicator(false); // CONF_EFIND
        c.setCreationDate(LocalDate.of(2004, 8, 17)); // CREATN_DT
        c.setDriverLicenseNumber("license"); // DRV_LIC_NO
        c.setGender(Gender.MALE); // GENDER_CD
        c.setIncapacitatedParentStatus(IncapacitatedParentStatus.UNKNOWN); // INCAPC_CD
        c.setLiterateStatus(LiterateStatus.UNKNOWN); // LITRATE_CD
        c.setMaritalCohabitationHistoryIndicator(false); // MAR_HIST_B
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

        c.setBirthStateCode((short) 0);//B_STATE_C
        c.setBirthCountryCode((short) 0); // B_CNTRY_C
        c.setImmigrationCountryCode((short) 0); // I_CNTRY_C
        c.setDriverLicenseStateCode((short) 0); // D_STATE_C
        c.setImmigrationStatusCode((short) 0); // IMGT_STC
        c.setMaritalStatusCode((short) 0); // MRTL_STC
        c.setPrimaryEthnicityCode((short) 0);//P_ETHNCTYC
        c.setSecondaryLanguageCode((short) 0);//S_LANG_TC
        c.setPrimaryLanguageCode((short) 0);//P_LANG_TPC
        c.setReligionCode((short) 0);//RLGN_TPC
        c.setSexualOrientationCode((short) 7066); //CLNT_SOC
        c.setSexualOrientationUnableToDetermineCode("D"); //SO_UD_CD
        c.setGenderIdentityCode((short) 7075); //CLNT_GIC
        c.setGenderEspressionCode((short) 7081);//CLNT_GEC
        dao.create(c);
      });

    IDataSet expectedDataSet = readXmlDataSet(expectedFilePath);
    ITable expectedTable = expectedDataSet.getTable("CLIENT_T");

    IDataSet actualDataSet = dbUnitConnection.createDataSet(new String[]{"CLIENT_T"});
    ITable actualTable = actualDataSet.getTable("CLIENT_T");

    assertTableEquals(expectedTable, actualTable);
  }

  @Test
  public void testUpdate() throws Exception {

    cleanAllAndInsert("/dbunit/Client/update/Client_before_update.xml");

    executeInTransaction(
      sessionFactory,
      (sessionFactory) -> {
        NameType nameType = nameTypeDao.find((short) 1314);
        assertNotNull(nameType);
        assertEquals("Maiden", nameType.getShortDescription().trim());
        assertTrue(nameType.getSystemId().equals((short) 1314));

        Client c = dao.find("AaiU7IW0Rt");
        assertEquals(0, c.getBirthStateCode());

        c.setNameType(nameType); // NAME_TPC
        dao.update(c);
      });

    IDataSet expectedDataSet = readXmlDataSet("/dbunit/Client/update/Client_after_update.xml");
    ITable expectedTable = expectedDataSet.getTable("CLIENT_T");

    IDataSet actualDataSet = dbUnitConnection.createDataSet(new String[]{"CLIENT_T"});
    ITable actualTable = actualDataSet.getTable("CLIENT_T");

    assertTableEquals(expectedTable, actualTable);
  }

  @Test
  public void testGetAccessTypeByAssignment() throws Exception {
    cleanAllAndInsert("/dbunit/ClientsByStaffPerson.xml");
    executeInTransaction(sessionFactory, (sessionFactory) -> {
      Assert.assertEquals(AccessType.RW, dao.getAccessTypeByAssignment("O9kIYi80Ki", "0Ki"));
      Assert.assertEquals(AccessType.R, dao.getAccessTypeByAssignment("AdQGgX00Ki", "0Ki"));
      Assert.assertEquals(AccessType.NONE, dao.getAccessTypeByAssignment("AzDQD9cN/A", "0Ki"));
    });
  }

  @Test
  public void findByFacilityIdAndChildId() throws Exception {
  }

  @Test
  public void findByLicNumAndChildId() throws Exception {
  }

  @Test
  public void streamByLicenseNumber() throws Exception {
  }

  @Test
  public void streamByLicenseNumber1() throws Exception {
  }

  @Test
  public void streamByFacilityId() throws Exception {
  }
}
