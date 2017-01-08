package gov.ca.cwds.data.es;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Reusable address class for Elasticsearch documents.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchAddress implements Serializable {

  /**
   * Base version. Increment by class version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("street_address")
  @Size(max = 50)
  @NotNull
  private String streetAddress;

  @JsonProperty("city")
  @Size(max = 50)
  private String city;

  @JsonProperty("state")
  @Size(max = 50)
  private String state;

  @JsonProperty("zip")
  private Integer zip;

  /**
   * Constructor.
   * 
   * @param streetAddress The street address
   * @param city The city
   * @param state The state
   * @param zip The zip
   */
  @JsonCreator
  public ElasticSearchAddress(@JsonProperty("street_address") String streetAddress,
      @JsonProperty("city") String city, @JsonProperty("state") String state,
      @JsonProperty("zip") Integer zip) {
    this.streetAddress = streetAddress;
    this.city = city;
    this.state = state;
    this.zip = zip;
  }

  /**
   * @return the street Address
   */
  public String getStreetAddress() {
    return streetAddress;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the zip
   */
  public Integer getZip() {
    return zip;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((city == null) ? 0 : city.hashCode());
    result = prime * result + ((state == null) ? 0 : state.hashCode());
    result = prime * result + ((streetAddress == null) ? 0 : streetAddress.hashCode());
    result = prime * result + ((zip == null) ? 0 : zip.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public final boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(getClass().isInstance(obj)))
      return false;
    ElasticSearchAddress other = (ElasticSearchAddress) obj;
    if (city == null) {
      if (other.city != null)
        return false;
    } else if (!city.equals(other.city))
      return false;
    if (state == null) {
      if (other.state != null)
        return false;
    } else if (!state.equals(other.state))
      return false;
    if (streetAddress == null) {
      if (other.streetAddress != null)
        return false;
    } else if (!streetAddress.equals(other.streetAddress))
      return false;
    if (zip == null) {
      if (other.zip != null)
        return false;
    } else if (!zip.equals(other.zip))
      return false;
    return true;
  }

}
