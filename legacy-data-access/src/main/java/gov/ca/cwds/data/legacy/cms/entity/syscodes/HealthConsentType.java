package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** @author CWDS CASE API Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "HL_CNSTC")
public class HealthConsentType extends SystemCodeTable {
  private static final long serialVersionUID = 5570204363159443078L;
}
