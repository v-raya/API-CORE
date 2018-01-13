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
@DiscriminatorValue(value = "SRV_CMPC")
@NamedQuery(name = ActiveServiceComponentType.NQ_ALL, query = "FROM ActiveServiceComponentType")
public class ActiveServiceComponentType extends SystemCodeTable {

    public static final String NQ_ALL = "ActiveServiceComponentType.all";

    private static final long serialVersionUID = 1L;

}
