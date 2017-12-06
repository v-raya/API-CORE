package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * The enum representing ASSIGNMENT.ESTABLISHED_FOR_CODE field of legacy system.
 * This code defines each type of recipient entity for which a specific ASSIGNMENT was made
 * (e.g., C = CASE, R = REFERRAL).
 *
 * @author CWDS TPT-3 Team
 */
public enum AssignmentRecipient implements EntityEnum<Character> {
  REFERRAL('R', "Referral"),
  CASE('C', "Case");

  private final Character code;
  private final String description;

  AssignmentRecipient(Character code, String description) {
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
  public static class AssignmentRecipientConverter extends BaseEntityEnumConverter<AssignmentRecipient, Character> {

    private static final Map<Character, AssignmentRecipient> codeMap =
        Collections.unmodifiableMap(initializeMapping(AssignmentRecipient.values()));

    @Override
    Map<Character, AssignmentRecipient> getCodeMap() {
      return codeMap;
    }
  }
}
