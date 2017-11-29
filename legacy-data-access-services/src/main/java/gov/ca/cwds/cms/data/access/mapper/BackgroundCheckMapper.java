package gov.ca.cwds.cms.data.access.mapper;

import gov.ca.cwds.cms.data.access.utils.IdGenerator;
import gov.ca.cwds.data.legacy.cms.entity.BackgroundCheck;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author CWDS CALS API Team
 */
@Mapper(imports = {LocalDateTime.class, LocalDate.class, IdGenerator.class})
public interface BackgroundCheckMapper {
  BackgroundCheckMapper INSTANCE = Mappers.getMapper(BackgroundCheckMapper.class);

  @Mapping(target = "identifier", expression = "java(IdGenerator.generateId(staffPersonId))")
  @Mapping(target = "rcpntCd", ignore = true) // TODO: 8/18/2017
  @Mapping(target = "rcpntId", ignore = true) // TODO: 8/18/2017
  @Mapping(target = "bkgrchkc", constant = "-1") // TODO: 8/17/2017
  @Mapping(target = "bkgrchkDt", expression = "java(LocalDate.now())") // TODO: 8/17/2017
  @Mapping(target = "lstUpdId", expression = "java(staffPersonId)")
  @Mapping(target = "lstUpdTs", expression = "java(LocalDateTime.now())")
  @Mapping(target = "fkcoltrlT", ignore = true)  // TODO: 8/17/2017
  BackgroundCheck toBackgroundCheck(String stub, String staffPersonId);
}
