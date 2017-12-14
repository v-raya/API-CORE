package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS CASE API Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "ETHNCTYC")
@NamedQuery(name = Ethnicity.NQ_ALL, query = "FROM Ethnicity")
public class Ethnicity extends SystemCodeTable {

  public static final String NQ_ALL = "Ethnicity.all";

  private static final long serialVersionUID = 5997623915767164418L;
}
