package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "HL_SCRDC")
public class HealthScreenedByType extends SystemCodeTable {
    private static final long serialVersionUID = -5140793099852675394L;
}
