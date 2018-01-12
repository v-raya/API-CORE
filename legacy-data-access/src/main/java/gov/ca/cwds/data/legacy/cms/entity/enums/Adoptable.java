package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum Adoptable implements EntityEnum<String> {
  NOT_ADOPTABLE("N", "No"),
  NOT_ASSESSED("NA", "NA"),
  ADOPTABLE("Y",  "Yes");

  private final String code;
  private final String description;

  Adoptable(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public static Adoptable from(String code) {
    return Arrays.asList(Adoptable.values())
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
  public static class AdoptableConverter extends BaseEntityEnumConverter<Adoptable,String> {

    private static final Map<String, Adoptable> codeMap =
        Collections.unmodifiableMap(initializeMapping(Adoptable.values()));

    @Override
    Map<String, Adoptable> getCodeMap() {
      return codeMap;
    }
  }
}

