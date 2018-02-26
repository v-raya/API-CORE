package gov.ca.cwds.data.legacy.cms.entity;

import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class PlacementFacilityTypeHistoryPK implements Serializable {

    private static final long serialVersionUID = -3497372643940393467L;
    @Id
    @Column(name = "FKCLIENT_T", nullable = false, length = 10)
    private String fkplcHmT;

    @Id
    @Column(name = "THIRD_ID", nullable = false, length = 10)
    private String thirdId;

    public PlacementFacilityTypeHistoryPK() {
    }

    public PlacementFacilityTypeHistoryPK(String fkplcHmT, String thirdId) {
        this.fkplcHmT = fkplcHmT;
        this.thirdId = thirdId;
    }

    public String getFkplcHmT() {
        return fkplcHmT;
    }

    public void setFkplcHmT(String fkplcHmT) {
        this.fkplcHmT = fkplcHmT;
    }

    public String getThirdId() {
        return thirdId;
    }

    public void setThirdId(String thirdId) {
        this.thirdId = thirdId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlacementFacilityTypeHistoryPK)) {
            return false;
        }
        PlacementFacilityTypeHistoryPK that = (PlacementFacilityTypeHistoryPK) o;
        return Objects.equal(fkplcHmT, that.fkplcHmT) &&
                Objects.equal(thirdId, that.thirdId);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(fkplcHmT, thirdId);
    }
}
