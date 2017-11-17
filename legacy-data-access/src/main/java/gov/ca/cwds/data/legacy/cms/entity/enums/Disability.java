package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * @author CWDS CASE API Team
 */
public enum Disability implements EntityEnum<Character> {
  NOT_YET_DETERMINED('D', "Not Yet Determined"),
  NO('N', "No"),
  YES('Y', "Yes");

  private final Character code;
  private final String description;

  Disability(Character code, String description) {
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
  public static class DisabilityConverter extends BaseEntityEnumConvertor<Disability, Character> {

    private static final Map<Character, Disability> codeMap =
        Collections.unmodifiableMap(initializeMapping(Disability.values()));

    @Override
    Map<Character, Disability> getCodeMap() {
      return codeMap;
    }
  }
}

