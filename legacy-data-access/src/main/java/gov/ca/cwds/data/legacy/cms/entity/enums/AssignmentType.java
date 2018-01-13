package gov.ca.cwds.data.legacy.cms.entity.enums;

import java.util.Collections;
import java.util.Map;
import javax.persistence.Converter;

/**
 * The enum representing ASSIGNMENT.TYPE_OF_ASSIGNMENT_CODE from legacy system. This indicator
 * specifies who has the lead role in carrying out the responsibilities of the CASE or REFERRAL. The
 * primary assignment can be made to either the case working STAFF_PERSON or to an
 * OUT_OF_STATE_CONTACT_PARTY. There may be many ASSIGNMENTs considered to be "primary" over the
 * life of the CASE or REFERRAL, but only one of them is current at any time. An ASSIGNMENT can be
 * either designated as Primary (P), Secondary (S), or Read Only (R). Primary assigned workers have
 * the capability to retrieve, read, and update any case or referral to which they have direct or
 * indirect assignment. Similarly, Secondary assigned workers also have the same capabilities to
 * retrieve, read, and update any case or referral to which they have direct or indirect assignment.
 * In fact, Secondary workers for the most part have the same capabilities as the Primary worker,
 * but occasionally there are business rules in the application which prohibit the Secondary worker
 * from doing something that the Primary worker can do (i.e. requesting the closure of an Adoptions
 * Case). Read Only assigned workers have the capability to access and read any case or referral to
 * which they have direct or indirect assignment, but they do not have update capability.
 *
 * @author CWDS TPT-3 Team
 */
public enum AssignmentType implements EntityEnum<String> {
  PRIMARY("P", "Primary"),
  SECONDARY("S", "Secondary"),
  READ_ONLY("R", "Read Only");

  private final String code;
  private final String description;

  AssignmentType(String code, String description) {
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
  public static class AssignmentTypeConverter
      extends BaseEntityEnumConverter<AssignmentType, String> {

    private static final Map<String, AssignmentType> codeMap =
        Collections.unmodifiableMap(initializeMapping(AssignmentType.values()));

    @Override
    Map<String, AssignmentType> getCodeMap() {
      return codeMap;
    }
  }
}
