package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/**
 * @author CWDS TPT-3 Team
 *     <p>REQUESTED_BY_TYPE - The system generated number assigned to record who requested the
 *     credit report. The selection choices will be provided by the Requested By Type code table,
 *     therefore this attribute will store the SysId of the chosen value.
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "CRPTREQC")
@NamedQuery(name = CreditReportRequestedByType.NQ_ALL, query = "FROM CreditReportRequestedByType")
public class CreditReportRequestedByType extends SystemCodeTable {

  public static final String NQ_ALL = "CreditReportRequestedByType.all";

  private static final long serialVersionUID = 8797921115181910447L;
}
