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
@DiscriminatorValue(value = "MRTL_STC")
@NamedQuery(name = MaritalStatus.NQ_ALL, query = "FROM MaritalStatus")
public class MaritalStatus extends SystemCodeTable {

    public static final String NQ_ALL = "MaritalStatus.all";

    private static final long serialVersionUID = 5983372111344075848L;
}
