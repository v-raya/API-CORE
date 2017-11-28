package gov.ca.cwds.cms.data.access.mapper;

import gov.ca.cwds.cms.data.access.Constants;
import gov.ca.cwds.cms.data.access.utils.IdGenerator;
import gov.ca.cwds.data.legacy.cms.entity.EmergencyContactDetail;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author CWDS CALS API Team
 */
@Mapper(imports = {LocalDateTime.class, IdGenerator.class, Constants.class})
public interface EmergencyContactDetailMapper {
  EmergencyContactDetailMapper INSTANCE = Mappers.getMapper(EmergencyContactDetailMapper.class);

  @Mapping(target = "identifier", expression = "java(IdGenerator.generateId(staffPersonId))")
  @Mapping(target = "estblshCd", constant = "P")
  @Mapping(target = "estblshId", source = "placementHomeId")
  @Mapping(target = "cntctNme", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "priPhNo", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "priPhExt", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "altPhNo", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "altPhExt", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "emailAddr", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "streetNo", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "streetNm", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "cityNm", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "stateC", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "zipNo", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "zipSfxNo", ignore = true) // TODO: 8/17/2017 mapping required
  @Mapping(target = "frgAdrtB", constant = Constants.N)
  @Mapping(target = "lstUpdId", source = "staffPersonId")
  @Mapping(target = "lstUpdTs", expression = "java(LocalDateTime.now())")
  EmergencyContactDetail toEmergencyContactDetail(String placementHomeId, String staffPersonId);
}
