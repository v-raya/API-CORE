package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.entity.BaseLicensingVisit;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Table(name = "LIC_VSTT")
public class LicensingVisit extends BaseLicensingVisit {

  private static final long serialVersionUID = -8288205929550791794L;
}
