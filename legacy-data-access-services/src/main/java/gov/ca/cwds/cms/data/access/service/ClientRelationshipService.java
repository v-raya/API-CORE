package gov.ca.cwds.cms.data.access.service;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipDTO;

/**
 * HOTLINE.
 *
 * @author CWDS TPT-3 Team
 */
public interface ClientRelationshipService {
  void createRelationship(ClientRelationshipDTO clientRelationshipDTO)
    throws DataAccessServicesException;
}
