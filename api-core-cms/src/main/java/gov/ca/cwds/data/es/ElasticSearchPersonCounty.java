package gov.ca.cwds.data.es;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Client county
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonCounty implements Serializable {

  private static final long serialVersionUID = 4396490810924002909L;

  @JsonProperty("county_id")
  private String countyId;

  @JsonProperty("county_name")
  private String countyName;

  /**
   * Default no-arg constructor
   */
  public ElasticSearchPersonCounty() {}

  /**
   * Get county id
   * 
   * @return The county id
   */
  public String getCountyId() {
    return countyId;
  }

  /**
   * Set county id
   * 
   * @param countyId The county id
   */
  public void setCountyId(String countyId) {
    this.countyId = countyId;
  }

  /**
   * Get county name
   * 
   * @return The county name
   */
  public String getCountyName() {
    return countyName;
  }

  /**
   * Set county name
   * 
   * @param countyName The county name
   */
  public void setCountyName(String countyName) {
    this.countyName = countyName;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }
}
