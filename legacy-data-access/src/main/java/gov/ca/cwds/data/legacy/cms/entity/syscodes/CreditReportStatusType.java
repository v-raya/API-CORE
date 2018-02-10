package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/**
 * @author CWDS TPT-3 Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "CRPTSTAC")
@NamedQuery(name = CreditReportStatusType.NQ_ALL, query = "FROM CreditReportStatusType")
public class CreditReportStatusType extends SystemCodeTable {

  public static final String NQ_ALL = "CreditReportStatusType.all";

  private static final long serialVersionUID = -1379332110966254425L;
}
