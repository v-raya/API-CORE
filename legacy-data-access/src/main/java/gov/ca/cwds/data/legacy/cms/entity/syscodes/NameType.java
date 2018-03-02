package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS CASE API Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "NAME_TPC")
@NamedQuery(name = NameType.NQ_ALL, query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.NameType")
public class NameType extends SystemCodeTable {

  public static final String NQ_ALL = "NameType.all";

  private static final long serialVersionUID = -8200692234744309298L;
}
