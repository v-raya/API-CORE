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
@DiscriminatorValue(value = "HL_TERMC")
@NamedQuery(name = HealthPlanTerminationType.NQ_ALL, query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.HealthPlanTerminationType")
public class HealthPlanTerminationType extends SystemCodeTable {

    public static final String NQ_ALL = "HealthPlanTerminationType.all";

    private static final long serialVersionUID = 1L;
}
