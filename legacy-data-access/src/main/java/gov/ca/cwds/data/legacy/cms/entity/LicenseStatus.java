package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.Cacheable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.NamedQuery;

/**
 * @author CWDS CALS API Team
 */
@Entity
@Cacheable
@DiscriminatorValue(value = "LIC_STC ")
@NamedQuery(name = LicenseStatus.NQ_ALL, query = "FROM LicenseStatus")
public class LicenseStatus extends SystemCodeTable {

    public static final String NQ_ALL = "LicenseStatus.all";

    private static final long serialVersionUID = 4960859625082727010L;
}
