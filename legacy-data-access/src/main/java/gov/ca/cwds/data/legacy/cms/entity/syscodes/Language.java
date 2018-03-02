package gov.ca.cwds.data.legacy.cms.entity.syscodes;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS CASE API Team */
@Entity
@Cacheable
@DiscriminatorValue(value = "LANG_TPC")
@NamedQuery(name = Language.NQ_ALL, query = "FROM gov.ca.cwds.data.legacy.cms.entity.syscodes.Language")
public class Language extends SystemCodeTable {

  public static final String NQ_ALL = "Language.all";

  private static final long serialVersionUID = 252301727081549268L;
}
