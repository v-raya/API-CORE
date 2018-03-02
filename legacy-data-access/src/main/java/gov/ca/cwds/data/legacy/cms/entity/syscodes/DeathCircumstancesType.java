package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;


/**
 * @author CWDS CASE API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "DTH_CIRC")
@NamedQuery(name = DeathCircumstancesType.NQ_ALL, query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.DeathCircumstancesType ORDER BY logicalId ASC")
public class DeathCircumstancesType extends SystemCodeTable {

    public static final String NQ_ALL = "DeathCircumstancesType.all";

    private static final long serialVersionUID = 1L;
}
