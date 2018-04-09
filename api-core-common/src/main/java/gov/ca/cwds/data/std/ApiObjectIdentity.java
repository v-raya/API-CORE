package gov.ca.cwds.data.std;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.core.JsonProcessingException;

import gov.ca.cwds.utils.JsonUtils;

/**
 * Convenient, default implementations of {@link #toString()}, {@link #hashCode()}, and {@link
 * #equals(Object)}.
 *
 * @author CWDS API Team
 */
public abstract class ApiObjectIdentity implements ApiMarker {

  private static final String[] EXCLUDED_FIELDS =
      new String[] {"identifier", "lastUpdatedId", "lastUpdatedTime"};

  private static final long serialVersionUID = 1L;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(
        17, 37, this, false, ApiObjectIdentity.class, EXCLUDED_FIELDS);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(
        this, obj, false, ApiObjectIdentity.class, EXCLUDED_FIELDS);
  }

  /**
   * Convenient method to stream JSON representation of concrete object.
   *
   * @return JSON string
   * @throws JsonProcessingException if unable to serialize JSON
   */
  public String toJson() throws JsonProcessingException {
    return JsonUtils.to(this);
  }
}
