package gov.ca.cwds.data.legacy.cms.entity.converter;

import javax.persistence.AttributeConverter;

/**
 * Created by TPT2 on 12/21/2017.
 */
public abstract class BaseZipConverter<T extends Number> implements AttributeConverter<String, T> {

  private final int length;

  BaseZipConverter(int length) {
    this.length = length;
  }

  @Override
  public T convertToDatabaseColumn(String s) {
    if (s == null) {
      return null;
    }
    return valueOf(s);

  }
  abstract T valueOf(String s);

  @Override
  public String convertToEntityAttribute(T integer) {
    if (integer == null || integer.intValue() == 0) {
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
