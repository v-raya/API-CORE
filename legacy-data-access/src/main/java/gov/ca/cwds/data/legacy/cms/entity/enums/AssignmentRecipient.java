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
public enum AssignmentRecipient implements EntityEnum<String> {
  CASE("C", "Case"),
  REFERRAL("R", "Referral");

  private final String code;
  private final String description;

  AssignmentRecipient(String code, String description) {
    this.code = code;
    this.description = description;
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
  public static class AssignmentRecipientConverter extends BaseEntityEnumConverter<AssignmentRecipient, String> {

    private static final Map<String, AssignmentRecipient> codeMap =
        Collections.unmodifiableMap(initializeMapping(AssignmentRecipient.values()));

    @Override
    Map<String, AssignmentRecipient> getCodeMap() {
      return codeMap;
    }
  }
}
