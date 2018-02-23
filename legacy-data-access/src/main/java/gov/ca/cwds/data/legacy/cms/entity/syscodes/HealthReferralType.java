package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "HL_REFC")
public class HealthReferralType extends SystemCodeTable {
    private static final long serialVersionUID = -8540497043578172653L;
}
