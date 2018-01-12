package gov.ca.cwds.cms.data.access.builder;

import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;

public interface EntityAwareDTOBuilder<T extends PersistentObject, P extends BaseEntityAwareDTO<T>> {
   P build();
}
