package gov.ca.cwds.data.legacy.cms.entity.converter;

import javax.persistence.AttributeConverter;

/**
 * @author CWDS TPT-2 Team
 *
 *
 * This converter converts string value to long and use zero as default value
 *
 */
public class RequiredShortConverter implements AttributeConverter<Short, Short> {

  @Override
  public Short convertToDatabaseColumn(Short value) {
    if (value == null) {
      return (short)0;
    }
    return value;
  }

  @Override
  public Short convertToEntityAttribute(Short dbData) {
    if (dbData == null || dbData == 0) {
      return null;
    }
    return dbData;
  }

}
