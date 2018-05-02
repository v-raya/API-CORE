package gov.ca.cwds.cms.data.access.service.lifecycle;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
public class DataAccessBundle<P extends BaseEntityAwareDTO> {
  private P awareDto;

  public DataAccessBundle(P awareDto) {
    this.awareDto = awareDto;
  }

  public P getAwareDto() {
    return awareDto;
  }

  public void setAwareDto(P awareDto) {
    this.awareDto = awareDto;
  }
}
