package gov.ca.cwds.data.legacy.cms.entity.converter;

import javax.persistence.AttributeConverter;

/**
 * @author CWDS TPT-2 Team
 *
 *
 * This converter converts string value to long and use zero as default value
 *
 */
public class StringToRequiredLongConverter implements AttributeConverter<String, Long> {

  @Override
  public Long convertToDatabaseColumn(String string) {
    if (string == null) {
      return 0L;
    }
    return Long.valueOf(string);
  }

  @Override
  public String convertToEntityAttribute(Long dbData) {
    if (dbData == null || dbData == 0) {
      return null;
    }
    return String.valueOf(dbData);
  }

}
