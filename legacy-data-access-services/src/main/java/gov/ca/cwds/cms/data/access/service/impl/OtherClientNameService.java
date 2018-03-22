package gov.ca.cwds.cms.data.access.service.impl;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.OtherClientNameDTO;
import gov.ca.cwds.data.legacy.cms.dao.OtherClientNameDao;
import gov.ca.cwds.data.legacy.cms.entity.OtherClientName;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType;
import gov.ca.cwds.security.utils.PrincipalUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static gov.ca.cwds.cms.data.access.service.impl.IdGenerator.generateId;

public class OtherClientNameService {
  private OtherClientNameDao otherClientNameDao;

  @Inject
  public OtherClientNameService(OtherClientNameDao otherClientNameDao) {
    this.otherClientNameDao = otherClientNameDao;
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

    // todo investigate and create phonetic search value

  }
}
