package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS CASE API Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "RLGN_TPC")
@NamedQuery(name = Religion.NQ_ALL, query = "FROM Religion")
public class Religion extends SystemCodeTable {

  public static final String NQ_ALL = "Religion.all";

  private static final long serialVersionUID = -7985907812552728539L;
}
