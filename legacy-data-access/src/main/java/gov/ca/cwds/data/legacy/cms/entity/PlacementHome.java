package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author CWDS CALS API Team
 */
@NamedQuery(
        name = "PlacementHome.find",
        query = "SELECT ph FROM PlacementHome ph "
                + " LEFT JOIN FETCH ph.countyLicenseCase cls"
                + " LEFT JOIN FETCH cls.staffPerson sp"
                + " LEFT JOIN FETCH cls.licensingVisits lv"
                + " WHERE ph.identifier = :facilityId"
)
@Entity
@Table(name = "PLC_HM_T")
public class PlacementHome extends BasePlacementHome {
    private static final long serialVersionUID = 8516376534560115439L;

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FKCNTY_CST", referencedColumnName = "IDENTIFIER")
    private CountyLicenseCase countyLicenseCase;

    @Override
    public CountyLicenseCase getCountyLicenseCase() {
        return countyLicenseCase;
    }

    public void setCountyLicenseCase(CountyLicenseCase countyLicenseCase) {
        this.countyLicenseCase = countyLicenseCase;
    }
}
