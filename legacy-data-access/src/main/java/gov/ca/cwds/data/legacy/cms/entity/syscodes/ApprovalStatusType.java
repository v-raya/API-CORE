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
@DiscriminatorValue(value = "APV_STC")
@NamedQuery(name = ApprovalStatusType.NQ_ALL, query = "FROM ApprovalStatusType")
public class ApprovalStatusType extends SystemCodeTable {

    public static final String NQ_ALL = "ApprovalStatusType.all";

    private static final long serialVersionUID = 1L;
}
