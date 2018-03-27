package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.Constants;
import gov.ca.cwds.cms.data.access.dao.SsaName3Dao;
import gov.ca.cwds.cms.data.access.dto.OtherClientNameDTO;
import gov.ca.cwds.data.legacy.cms.dao.OtherClientNameDao;
import gov.ca.cwds.data.legacy.cms.dao.SsaName3ParameterObject;
import gov.ca.cwds.data.legacy.cms.entity.OtherClientName;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import gov.ca.cwds.security.utils.PrincipalUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static gov.ca.cwds.cms.data.access.Constants.PhoneticPrimaryNameCode.OTHER_CLIENT_NAME;
import static gov.ca.cwds.cms.data.access.Constants.SsaName3StoredProcedureCrudOperationCode.INSERT_OPERATION_CODE;
import static gov.ca.cwds.cms.data.access.service.impl.IdGenerator.generateId;

public class OtherClientNameService {
  private OtherClientNameDao otherClientNameDao;
  private SsaName3Dao ssaName3Dao;

  @Inject
  public OtherClientNameService(OtherClientNameDao otherClientNameDao, SsaName3Dao ssaName3Dao) {
    this.otherClientNameDao = otherClientNameDao;
    this.ssaName3Dao = ssaName3Dao;
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

    persistPhoneticSearchKeywords(otherClientName);
  }

  private void persistPhoneticSearchKeywords(OtherClientName otherClientName) {
    SsaName3ParameterObject parameterObject = new SsaName3ParameterObject();
    parameterObject.setTableName(Constants.PhoneticSearchTables.CLT_PHNT);
    parameterObject.setCrudOper(INSERT_OPERATION_CODE);
    parameterObject.setNameCd(OTHER_CLIENT_NAME);
    parameterObject.setIdentifier(otherClientName.getThirdId());
    parameterObject.setFirstName(otherClientName.getFirstName());
    parameterObject.setLastName(otherClientName.getLastName());
    parameterObject.setMiddleName(otherClientName.getMiddleName());
    parameterObject.setUpdateTimeStamp(new Date());
    parameterObject.setUpdateId(otherClientName.getLastUpdateId());
    ssaName3Dao.callStoredProc(parameterObject);
  }
}
