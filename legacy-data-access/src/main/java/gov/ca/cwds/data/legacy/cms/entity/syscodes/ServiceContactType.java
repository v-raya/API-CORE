package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS TPT-3 Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "SVC_CNTC")
@NamedQuery(name = ServiceContactType.NQ_ALL, query = "FROM ServiceContactType")
public class ServiceContactType extends SystemCodeTable {
  public static final String NQ_ALL = "ServiceContactType.all";

  private static final long serialVersionUID = 1L;
}
