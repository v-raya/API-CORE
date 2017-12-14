package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

public enum Soc158placementsStatus implements EntityEnum<Character> {
  MIGRATED_TO_CLIENT_SERVICES('M', "Migrated SOC 158 Placements to Client Services"),
  NO_SOC_158_PLACEMENTS('N', "No SOC 158 Placements"),
  NOT_YET_MIGRATED('Y', "SOC 158 Placements not yet migrated");

  private final Character code;
  private final String description;

  Soc158placementsStatus(Character code, String description) {
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
  public static class Soc158placementsStatusConverter
      extends BaseEntityEnumConverter<Soc158placementsStatus, Character> {

    private static final Map<Character, Soc158placementsStatus> codeMap =
        Collections.unmodifiableMap(initializeMapping(Soc158placementsStatus.values()));

    @Override
    Map<Character, Soc158placementsStatus> getCodeMap() {
      return codeMap;
    }
  }
}
