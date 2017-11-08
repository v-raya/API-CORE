package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "VIST_TPC")
public class VisitType extends SystemCodeTable {
    private static final long serialVersionUID = 42L;
}
