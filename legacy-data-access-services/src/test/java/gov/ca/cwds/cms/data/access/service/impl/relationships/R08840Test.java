package gov.ca.cwds.cms.data.access.service.impl.relationships;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.cms.data.access.dto.ClientRelationshipAwareDTO;
import gov.ca.cwds.cms.data.access.service.rules.ClientRelationshipDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import gov.ca.cwds.drools.DroolsException;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R08840Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME = "R-08840-CT";

  @Test
  public void testRelationshipTypeIsMatch() throws DroolsException {
    enrichRelationshipWithMatchedType();
    businessValidationService.runDataProcessing(
        awareDTO, principal, ClientRelationshipDroolsConfiguration.DATA_PROCESSING_INSTANCE);

    assertEquals(awareDTO.isNeedMembershipVerification(), true);
  }

  private void enrichRelationshipWithMatchedType() {
    ClientRelationship entity = new ClientRelationship();
    ClientRelationshipType clientRelationshipType = new ClientRelationshipType();
    clientRelationshipType.setSystemId((short) 190);
    entity.setType(clientRelationshipType);
    awareDTO.setEntity(entity);
  }
}
