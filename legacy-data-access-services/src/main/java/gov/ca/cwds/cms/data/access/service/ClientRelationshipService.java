package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;

/**
 * HOTLINE.
 *
 * @author CWDS TPT-3 Team
 */
@FunctionalInterface
public interface ClientRelationshipService {

  ClientRelationship createRelationship(ClientRelationshipDTO clientRelationshipDto)
      throws DataAccessServicesException;

}
