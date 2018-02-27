package gov.ca.cwds.cms.data.access.service.impl;

import gov.ca.cwds.data.persistence.cms.CmsKeyIdGenerator;
import gov.ca.cwds.security.utils.PrincipalUtils;

/**
 * @author CWDS CALS API Team
 */
public class IdGenerator {

  private IdGenerator() {
  }

  public static String generateId() {
      return CmsKeyIdGenerator.getNextValue(PrincipalUtils.getStaffPersonId());
  }
}

