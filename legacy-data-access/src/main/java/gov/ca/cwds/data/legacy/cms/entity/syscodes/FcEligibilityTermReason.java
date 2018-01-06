package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS TPT-3 Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "ELG_TRMC")
@NamedQuery(name = FacilityType.NQ_ALL, query = "FROM FcEligibilityTermReason")
public class FcEligibilityTermReason extends SystemCodeTable {

  public static final String NQ_ALL = "FcEligibilityTermReason.all";

  private static final long serialVersionUID = 1L;
}
