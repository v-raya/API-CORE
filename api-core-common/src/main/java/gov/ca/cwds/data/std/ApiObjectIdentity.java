package gov.ca.cwds.data.std;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Convenient, default implementations of {@link #toString()}, {@link #hashCode()}, and {@link
 * #equals(Object)}.
 *
 * @author CWDS API Team
 */
public abstract class ApiObjectIdentity implements ApiMarker {

  private static final String[] EXCLUDED_FIELDS = new String[] {"IDENTIFIER"};

  /** Default serialization. */
  private static final long serialVersionUID = 1L;

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE, true);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(
        1, 37, this, false, ApiObjectIdentity.class, EXCLUDED_FIELDS);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(
        this, obj, false, ApiObjectIdentity.class, EXCLUDED_FIELDS);
  }
}
