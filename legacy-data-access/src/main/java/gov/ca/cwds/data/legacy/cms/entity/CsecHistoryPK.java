package gov.ca.cwds.data.legacy.cms.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author CWDS TPT-3 Team
 */
public class CsecHistoryPK implements Serializable {

  private static final long serialVersionUID = 6232629275700964101L;
  private String childClient;
  private String thirdId;

  public CsecHistoryPK() {}

  public CsecHistoryPK(String childClient, String thirdId) {
    this.childClient = childClient;
    this.thirdId = thirdId;
  }

  public String getChildClient() {
    return childClient;
  }

  public String getThirdId() {
    return thirdId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    CsecHistoryPK that = (CsecHistoryPK) o;

    return new EqualsBuilder()
        .append(childClient, that.childClient)
        .append(thirdId, that.thirdId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(childClient).append(thirdId).toHashCode();
  }
}
