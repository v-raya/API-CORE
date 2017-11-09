package gov.ca.cwds.data.legacy.cms.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
@Converter
public class LimitedAccessConverter implements AttributeConverter<LimitedAccess, String> {

  @Override
  public String convertToDatabaseColumn(LimitedAccess value) {
    if (value == null) {
      return null;
    }
    return value.getCode();
  }

  @Override
  public LimitedAccess convertToEntityAttribute(String value) {
    if (value == null) {
      return null;
    }
    return LimitedAccess.fromCode(value);
  }
}
