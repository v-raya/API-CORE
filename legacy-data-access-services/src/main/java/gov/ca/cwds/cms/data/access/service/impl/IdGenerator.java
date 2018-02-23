package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.security.utils.PrincipalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author CWDS CALS API Team
 */
class IdGenerator {

  private IdGenerator() {
  }

  public static String generateId() {
      return CmsKeyIdGenerator.getNextValue(PrincipalUtils.getStaffPersonId());
  }
}

