package gov.ca.cwds.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * @author CWDS CALS API Team
 */
@JsonIgnoreProperties({"messages"})
public abstract class BaseDTO implements Serializable {

  private static final long serialVersionUID = -2099354387049882669L;

  @Override
  public String toString() {
    return ReflectionToStringBuilder.toString(this);
  }

  @Override
  public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
}
