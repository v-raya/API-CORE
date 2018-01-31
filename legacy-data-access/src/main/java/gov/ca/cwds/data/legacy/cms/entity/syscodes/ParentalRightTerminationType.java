package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "PRTERM_C")
public class ParentalRightTerminationType extends SystemCodeTable {
  private static final long serialVersionUID = -477660288071506104L;
}
