package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS TPT-3 Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "INDN_STC")
@NamedQuery(
  name = IndianEnrolmentStatus.NQ_ALL,
  query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.IndianEnrolmentStatus"
)
public class IndianEnrolmentStatus extends SystemCodeTable {

  public static final String NQ_ALL = "IndianEnrolmentStatus.all";

  private static final long serialVersionUID = 5310777832779657693L;
}
