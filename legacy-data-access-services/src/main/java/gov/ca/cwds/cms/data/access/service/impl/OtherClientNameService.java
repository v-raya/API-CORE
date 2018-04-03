package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.Constants;
import gov.ca.cwds.cms.data.access.dto.OtherClientNameDTO;
import gov.ca.cwds.data.legacy.cms.dao.ClientUcDao;
import gov.ca.cwds.data.legacy.cms.dao.OtherClientNameDao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3Dao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.ClientUc;
import gov.ca.cwds.data.legacy.cms.entity.OtherClientName;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static gov.ca.cwds.cms.data.access.service.impl.IdGenerator.generateId;

public class OtherClientNameService {
  private OtherClientNameDao otherClientNameDao;
  private SsaName3Dao ssaName3Dao;
  private ClientUcDao clientUcDao;

  @Inject
  public OtherClientNameService(
      OtherClientNameDao otherClientNameDao, SsaName3Dao ssaName3Dao, ClientUcDao clientUcDao) {
    this.otherClientNameDao = otherClientNameDao;
    this.ssaName3Dao = ssaName3Dao;
    this.clientUcDao = clientUcDao;
  }

  public List<OtherClientName> findByClientId(String clientId) {
    return otherClientNameDao.findByClientId(clientId);
  }

  public void createOtherClientName(OtherClientNameDTO otherClientNameDTO) {
    OtherClientName otherClientName = new OtherClientName();

    otherClientName.setClientId(otherClientNameDTO.getClientId());
    otherClientName.setNamePrefixDescription(
        Objects.toString(otherClientNameDTO.getNamePrefixDescription(), ""));
    otherClientName.setFirstName(Objects.toString(otherClientNameDTO.getFirstName(), ""));
    otherClientName.setLastName(Objects.toString(otherClientNameDTO.getLastName(), ""));
    otherClientName.setSuffixTitleDescription(
        Objects.toString(otherClientNameDTO.getSuffixTitleDescription(), ""));
    otherClientName.setMiddleName(Objects.toString(otherClientNameDTO.getMiddleName(), ""));

    otherClientName.setThirdId(generateId());
    NameType nameType = new NameType();
    nameType.setSystemId(otherClientNameDTO.getNameType());
    otherClientName.setNameType(nameType);
    otherClientName.setLastUpdateId(PrincipalUtils.getStaffPersonId());
    otherClientName.setLastUpdateTime(LocalDateTime.now());
    otherClientNameDao.create(otherClientName);
    createClientNameUc(otherClientName);
    createPhoneticSearchKeywords(otherClientName);
  }

  /*
  R - 06015 DocTool rule is implemented here

  <p>Rule text:
  When adding an Other Client Name, add corresponding row to CLIENT_UC table
  and set Client_Source_Table_Code to N.
   */
  private void createClientNameUc(OtherClientName otherClientName) {
    ClientUc clientUc = new ClientUc();
    clientUc.setCommonLastName(StringUtils.upperCase(otherClientName.getLastName()));
    clientUc.setCommonMiddleName(StringUtils.upperCase(otherClientName.getMiddleName()));
    clientUc.setCommonFirstName(StringUtils.upperCase(otherClientName.getFirstName()));
    clientUc.setSourceTableId(otherClientName.getThirdId());
    clientUc.setSourceTableCode(Constants.ClientUcSourceTableCode.OTHER_CLIENT_NAME);
    clientUcDao.create(clientUc);
  }

  /*
  R - 10841 DocTool rule is implemented here
  Maintain Client Phonetic Entity

  <p>Rule text:
  When a Other Names first, middle or last name field is added, make the appropriate update to the corresponding
  Client Phonetic rows.
  */
  private void createPhoneticSearchKeywords(OtherClientName otherClientName) {
    SsaName3ParameterObject parameterObject = new SsaName3ParameterObject();
    parameterObject.setTableName(Constants.PhoneticSearchTables.CLT_PHNT);
    parameterObject.setCrudOper(
        Constants.SsaName3StoredProcedureCrudOperationCode.INSERT_OPERATION_CODE);
    parameterObject.setNameCd(Constants.PhoneticPrimaryNameCode.OTHER_CLIENT_NAME);
    parameterObject.setIdentifier(otherClientName.getThirdId());
    parameterObject.setFirstName(otherClientName.getFirstName());
    parameterObject.setLastName(otherClientName.getLastName());
    parameterObject.setMiddleName(otherClientName.getMiddleName());
    parameterObject.setUpdateTimeStamp(new Date());
    parameterObject.setUpdateId(otherClientName.getLastUpdateId());
    ssaName3Dao.callStoredProc(parameterObject);
  }
}
