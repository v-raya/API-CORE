package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.enums.YesNoUnknown;
import gov.ca.cwds.data.persistence.cms.BaseClient;
import java.time.LocalDate;

/**
 * Hotline (create relationship).
 *
 * @author CWDS TPT-3 Team
 * */
public class ClientRelationshipDTO {

  private String identifier;
  private BaseClient primaryClient;
  private BaseClient secondaryClient;
  private short type;
  private LocalDate startDate;
  private LocalDate endDate;
  private boolean absentParentIndicator;
  private YesNoUnknown sameHomeStatus;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public BaseClient getPrimaryClient() {
    return primaryClient;
  }

  public void setPrimaryClient(BaseClient primaryClient) {
    this.primaryClient = primaryClient;
  }

  public BaseClient getSecondaryClient() {
    return secondaryClient;
  }

  public void setSecondaryClient(BaseClient secondaryClient) {
    this.secondaryClient = secondaryClient;
  }

  public short getType() {
    return type;
  }

  public void setType(short type) {
    this.type = type;
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

  public boolean isAbsentParentIndicator() {
    return absentParentIndicator;
  }

  public void setAbsentParentIndicator(boolean absentParentIndicator) {
    this.absentParentIndicator = absentParentIndicator;
  }

  public YesNoUnknown getSameHomeStatus() {
    return sameHomeStatus;
  }

  public void setSameHomeStatus(YesNoUnknown sameHomeStatus) {
    this.sameHomeStatus = sameHomeStatus;
  }
}
