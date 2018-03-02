package gov.ca.cwds.data.legacy.cms.entity;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class PlacementFacilityTypeHistoryPK implements Serializable {

    private static final long serialVersionUID = -3497372643940393467L;
    @Id
    @Column(name = "FKPLC_HM_T", nullable = false, length = 10)
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
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public final int hashCode() {
        return  HashCodeBuilder.reflectionHashCode(this);
    }
}
