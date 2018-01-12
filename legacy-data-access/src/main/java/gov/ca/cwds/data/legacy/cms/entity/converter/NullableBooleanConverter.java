package gov.ca.cwds.data.legacy.cms.entity.converter;

import javax.persistence.AttributeConverter;
import org.apache.commons.lang3.StringUtils;

public class NullableBooleanConverter implements AttributeConverter<Boolean, String> {

  @Override
  public String convertToDatabaseColumn(Boolean attribute) {
    if (attribute == null) {
      return null;
    } else {
      return (attribute ? "Y" : "N");
    }
  }

  @Override
  @SuppressWarnings("squid:S2447")
  public Boolean convertToEntityAttribute(String dbData) {
    if (StringUtils.isBlank(dbData)) {
      return null;
    }
    return "Y".equalsIgnoreCase(dbData.trim()) ? Boolean.TRUE : Boolean.FALSE;
  }
}
