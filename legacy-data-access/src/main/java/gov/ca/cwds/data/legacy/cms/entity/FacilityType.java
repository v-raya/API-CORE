package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;


/**
 * @author CWDS CALS API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "PLC_FCLC")
@NamedQuery(name = FacilityType.NQ_ALL, query = "FROM FacilityType")
public class FacilityType extends SystemCodeTable {

    public static final String NQ_ALL = "FacilityType.all";

    private static final long serialVersionUID = -532213571875852577L;

}
