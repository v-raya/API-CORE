package gov.ca.cwds.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author CWDS CALS API Team
 */

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum IssueType {

  JSON_PROCESSING_EXCEPTION("json_processing_exception"),
  BUSINESS_VALIDATION("business_validation"),
  CONSTRAINT_VALIDATION("constraint_validation"),
  UNEXPECTED_EXCEPTION("unexpected_exception"),
  EXPECTED_EXCEPTION("expected_exception");

  private String value;

  IssueType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

}
