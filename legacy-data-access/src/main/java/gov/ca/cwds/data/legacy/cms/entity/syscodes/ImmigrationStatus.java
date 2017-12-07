package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;


/**
 * @author CWDS CALS API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "IMGT_STC")
@NamedQuery(name = ImmigrationStatus.NQ_ALL, query = "FROM ImmigrationStatus")
public class ImmigrationStatus extends SystemCodeTable {

    public static final String NQ_ALL = "ImmigrationStatus.all";

    private static final long serialVersionUID = 3059903545931810408L;
}
