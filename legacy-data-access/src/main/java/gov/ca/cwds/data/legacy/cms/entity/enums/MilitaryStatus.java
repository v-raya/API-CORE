package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum MilitaryStatus implements EntityEnum<Character> {
  MILITARY_ACTIVE('A', "Military active"),
  MILITARY_DEPENDENT('D', "Military dependent"),
  NO_MILITARY_INVOLVEMENT('N', "No military involvement"),
  NO_INFORMATION_AVAILABLE('U', "No information available"),
  VETERAN('V', "Veteran");

  private final Character code;
  private final String description;

  MilitaryStatus(Character code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public Character getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Converter
  public static class MilitaryStatusConverter
      extends BaseEntityEnumConverter<MilitaryStatus, Character> {

    private static final Map<Character, MilitaryStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(MilitaryStatus.values()));

    @Override
    Map<Character, MilitaryStatus> getCodeMap() {
      return codeMap;
    }
  }
}
