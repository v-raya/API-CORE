package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum MilitaryStatus implements EntityEnum<String> {
  MILITARY_ACTIVE("A", "Military active"),
  MILITARY_DEPENDENT("D", "Military dependent"),
  NO_MILITARY_INVOLVEMENT("N", "No military involvement"),
  NO_INFORMATION_AVAILABLE("U", "No information available"),
  VETERAN("V", "Veteran");

  private final String code;
  private final String description;

  MilitaryStatus(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static MilitaryStatus from(String code) {
    return Arrays.asList(MilitaryStatus.values())
        .stream()
        .findFirst()
        .filter(e -> e.code.equals(code))
        .orElse(null);
  }

  @Override
  public String getCode() {
    return code;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Converter
  public static class MilitaryStatusConverter
      extends BaseEntityEnumConverter<MilitaryStatus, String> {

    private static final Map<String, MilitaryStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(MilitaryStatus.values()));

    @Override
    Map<String, MilitaryStatus> getCodeMap() {
      return codeMap;
    }
  }
}
