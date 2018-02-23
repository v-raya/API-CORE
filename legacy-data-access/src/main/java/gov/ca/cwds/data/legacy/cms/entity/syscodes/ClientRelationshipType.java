package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS CASE API Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "CLNTRELC")
@NamedQuery(name = ClientRelationshipType.NQ_ALL, query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.ClientRelationshipType")
public class ClientRelationshipType extends SystemCodeTable {

  public static final String NQ_ALL = "ClientRelationshipType.all";

  private static final long serialVersionUID = 2198417690871212913L;
}
