package gov.ca.cwds.data.legacy.cms.entity.facade;

import gov.ca.cwds.data.std.ApiMarker;
import java.time.LocalDate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ClientCounty implements ApiMarker {

  private static final long serialVersionUID = 2L;

  public static final String MAPPING_CLIENT_COUNTY = "ClientCounty.mapping";
  public static final String QUERY_CLIENT_COUNTY = "Client.getClientCounty";

  private String rule;
  private Integer countyCode;
  private LocalDate startDate;
  private LocalDate endDate;
  private String identifier;

  public ClientCounty(String rule, Integer countyCode, LocalDate startDate,
    LocalDate endDate, String identifier) {
    this.rule = rule;
    this.countyCode = countyCode;
    this.startDate = startDate;
    this.endDate = endDate;
    this.identifier = identifier;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public Integer getCountyCode() {
    return countyCode;
  }

  public void setCountyCode(Integer countyCode) {
    this.countyCode = countyCode;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
