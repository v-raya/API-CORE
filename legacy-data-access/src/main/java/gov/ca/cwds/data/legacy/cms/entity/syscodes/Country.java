package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import static gov.ca.cwds.data.legacy.cms.entity.syscodes.Country.NQ_ALL;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS CASE API Team */
@NamedQuery(name = NQ_ALL, query = "FROM Country ORDER BY lgcId ASC")
@NamedQuery(name = "Country.findByLogicalId", query = "FROM Country where lgcId = :logicalId")
@Entity
@Cacheable
@DiscriminatorValue(value = "CNTRY_C")
public class Country extends SystemCodeTable {

  private static final long serialVersionUID = 1L;

  public static final String NQ_ALL = "Country.all";
}
