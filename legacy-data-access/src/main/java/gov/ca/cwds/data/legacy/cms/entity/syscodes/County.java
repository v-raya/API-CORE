package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import static gov.ca.cwds.data.legacy.cms.entity.syscodes.County.NQ_ALL;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS CALS API Team */
@NamedQuery(name = NQ_ALL, query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.County ORDER BY logicalId ASC")
@NamedQuery(name = "County.findByLogicalId", query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.County where logicalId = :logicalId")
@Entity
@Cacheable
@DiscriminatorValue(value = "GVR_ENTC")
public class County extends SystemCodeTable {

  private static final long serialVersionUID = -6062668668709817218L;

  public static final String NQ_ALL = "County.all";
}
