package gov.ca.cwds.data.persistence.cms.legacy;

import gov.ca.cwds.data.persistence.cms.BaseStaffPerson;
import javax.persistence.Entity;

/**
 * @author CWDS CALS API Team
 */
@Entity
@javax.persistence.Table(name = "STFPERST")
public class StaffPerson extends BaseStaffPerson {

  private static final long serialVersionUID = 5518501308828983602L;
}
