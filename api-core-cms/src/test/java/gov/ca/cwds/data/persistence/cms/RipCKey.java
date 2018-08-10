package gov.ca.cwds.data.persistence.cms;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import gov.ca.cwds.data.std.ApiObjectIdentity;
import gov.ca.cwds.rest.api.domain.DomainChef;

final class RipCKey extends ApiObjectIdentity {

  private static final long serialVersionUID = 1L;

  private final String key;
  private final String staffId;
  private final Date date;
  private final String ui19Digit;
  private final String newKey;

  // key, staff id, ISO 8601 date, Timestamp (hr.min.sec.1/100 sec), UI Timestamp, UI 19-digit
  public RipCKey(String line) {
    final String[] tokens = line.split("\t");
    this.key = tokens[0];
    this.staffId = tokens[1];
    this.date = DomainChef.uncookISO8601Timestamp(tokens[2]);
    this.ui19Digit = tokens[4];
    this.newKey = tokens[5];
  }

  public String getKey() {
    return key;
  }

  public String getStaffId() {
    return staffId;
  }

  public Date getDate() {
    return date;
  }

  public String getUi19Digit() {
    return ui19Digit;
  }

  public String regenerate() {
    return JavaKeyCmdLine.generateKey(staffId);
  }

  public boolean validate() {
    final String javaKey = regenerate();
    if (!javaKey.equals(key)) {
      JavaKeyCmdLine.LOGGER.warn("QUESTIONABLE KEY! staff: {}, timestamp: {}, key: {}", staffId, date, key);
    }

    return javaKey.equals(newKey);
  }

  public String getNewKey() {
    return newKey;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}