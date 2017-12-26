package gov.ca.cwds.data.legacy.cms.entity.converter;

import javax.persistence.AttributeConverter;

/**
 * Created by TPT2 on 12/21/2017.
 */
public abstract class BaseZipConverter implements AttributeConverter<String, Integer> {

  private final int length;

  BaseZipConverter(int length) {
    this.length = length;
  }

  @Override
  public Integer convertToDatabaseColumn(String s) {
    if (s == null) {
      return 0;
    }
    return Integer.valueOf(s);
  }

  @Override
  public String convertToEntityAttribute(Integer integer) {
    if (integer == null || integer == 0) {
      return null;
    }
    String zip = integer.toString();
    if(zip.length() == length) {
      return zip;
    }
    StringBuilder attribute = new StringBuilder(zip).reverse();
    for (int i = attribute.length(); i < length; i++) {
      attribute.append('0');
    }
    return attribute.reverse().toString();
  }
}
