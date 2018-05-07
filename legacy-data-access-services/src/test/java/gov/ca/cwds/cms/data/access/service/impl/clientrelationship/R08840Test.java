package gov.ca.cwds.cms.data.access.service.impl.clientrelationship;

import static org.junit.Assert.assertEquals;

import gov.ca.cwds.cms.data.access.service.rules.ClientRelationshipDroolsConfiguration;
import gov.ca.cwds.data.legacy.cms.entity.ClientRelationship;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType;
import gov.ca.cwds.drools.DroolsException;
import org.junit.Test;

/** @author CWDS TPT-3 Team */
public class R08840Test extends BaseDocToolRulesChildClientRelationshipTest {

  private static final String RULE_NAME = "R-08840";

  @Test
  public void testRelationshipTypeIsMatch() throws DroolsException {
    enrichRelationshipWithType((short) 190);
    businessValidationService.runDataProcessing(
        awareDTO, principal, ClientRelationshipDroolsConfiguration.DATA_PROCESSING_INSTANCE);

    assertEquals(awareDTO.isNeedMembershipVerification(), true);
  }

  @Test
  public void testRelationshipTypeIsNotMatch() throws DroolsException {
    enrichRelationshipWithType((short) 191);
    businessValidationService.runDataProcessing(
        awareDTO, principal, ClientRelationshipDroolsConfiguration.DATA_PROCESSING_INSTANCE);

    assertEquals(awareDTO.isNeedMembershipVerification(), false);
  }

  private void enrichRelationshipWithType(short type) {
    ClientRelationship entity = new ClientRelationship();
    ClientRelationshipType clientRelationshipType = new ClientRelationshipType();
    clientRelationshipType.setSystemId(type);
    entity.setType(clientRelationshipType);
    awareDTO.setEntity(entity);
  }
}
