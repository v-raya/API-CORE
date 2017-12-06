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
@DiscriminatorValue(value = "ASSG_RLC")
@NamedQuery(name = SecondaryAssignmentRoleType.NQ_ALL, query = "FROM SecondaryAssignmentRoleType")
public class SecondaryAssignmentRoleType extends SystemCodeTable {

  private static final long serialVersionUID = 1417537320344197400L;

  public static final String NQ_ALL = "SecondaryAssignmentRoleType.all";

}
