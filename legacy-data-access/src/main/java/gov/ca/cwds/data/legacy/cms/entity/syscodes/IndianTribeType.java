package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS TPT-3 Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "IDN_TRBC ")
@NamedQuery(
  name = IndianTribeType.NQ_ALL,
  query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.IndianTribeType"
)
public class IndianTribeType extends SystemCodeTable {

  public static final String NQ_ALL = "IndianTribeType.all";

  private static final long serialVersionUID = -7796779331643619939L;
}
