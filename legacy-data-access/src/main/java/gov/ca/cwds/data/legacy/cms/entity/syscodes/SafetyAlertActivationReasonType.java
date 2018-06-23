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
@DiscriminatorValue(value = "ACTV_RNC")
@NamedQuery(name = SafetyAlertActivationReasonType.NQ_ALL,
    query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.SafetyAlertActivationReasonType")
@NamedQuery(name = "SafetyAlertActivationReasonType.findBySystemId",
    query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.SafetyAlertActivationReasonType where systemId = :systemId")
public class SafetyAlertActivationReasonType extends SystemCodeTable {

  public static final String NQ_ALL = "SafetyAlertActivationReasonType.all";
  private static final long serialVersionUID = -4032664807617057298L;

}
