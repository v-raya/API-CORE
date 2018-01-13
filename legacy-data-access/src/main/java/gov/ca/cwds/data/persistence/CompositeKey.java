package gov.ca.cwds.data.persistence;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/** @author CWDS TPT-3 Team */
public class CompositeKey implements Serializable {
  private static final long serialVersionUID = 1L;

  private String id1 = "";
  private String id2 = "";

  public CompositeKey() {}

  public CompositeKey(String id1, String id2) {
    this.id1 = id1;
    this.id2 = id2;
  }

  @Override
  public String toString() {
    return "embed_key2__{" + id1.trim() + "_" + id2.trim() + "}";
  }

  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public final boolean equals(Object obj) {
    // Reduce cognitive complexity and improve accuracy for a slight performance hit.
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  public String getId1() {
    return id1;
  }

  public void setId1(String id1) {
    this.id1 = id1;
  }

  public String getId2() {
    return id2;
  }

  public void setId2(String id2) {
    this.id2 = id2;
  }
}
