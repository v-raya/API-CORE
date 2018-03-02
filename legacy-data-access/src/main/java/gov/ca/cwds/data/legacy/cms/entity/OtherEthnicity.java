package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author CWDS CALS API Team
 *
 * Associates a specific ethnicity (e.g., Black, Chinese, Hawaiian) with a specific CLIENT or SCP.
 */
@Entity
@DiscriminatorColumn(name = "ESTBLSH_CD")
@Table(name = "CLSCP_ET")
@SuppressWarnings({"squid:S3437", "squid:S2160"})
public abstract class OtherEthnicity extends CmsPersistentObject {

    private static final long serialVersionUID = 9107790326430678145L;

    @Id
    @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
    @Column(name = "IDENTIFIER")
    private String id;

    @Column(name = "ETHNCTYC")
    private short ethnicityCode;

    @Override
    public Serializable getPrimaryKey() {
        return getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public short getEthnicityCode() {
        return ethnicityCode;
    }

    public void setEthnicityCode(short ethnicityCode) {
        this.ethnicityCode = ethnicityCode;
    }
}
