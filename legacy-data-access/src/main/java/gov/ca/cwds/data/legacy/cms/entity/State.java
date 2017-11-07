package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "STATE_C ")
@NamedQuery(name = State.NQ_ALL, query = "FROM State")
public class State extends SystemCodeTable {

  public static final String NQ_ALL = "State.all";

  private static final long serialVersionUID = -6062856327873602890L;

}