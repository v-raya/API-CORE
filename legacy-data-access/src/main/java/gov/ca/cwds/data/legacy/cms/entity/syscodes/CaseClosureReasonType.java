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
@DiscriminatorValue(value = "CLS_RSNC")
@NamedQuery(name = CaseClosureReasonType.NQ_ALL, query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.CaseClosureReasonType")
public class CaseClosureReasonType extends SystemCodeTable {

    public static final String NQ_ALL = "CaseClosureReasonType.all";

    private static final long serialVersionUID = 1L;
}
