package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.persistence.PersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

/** @author CWDS TPT-3 Team Special Education information for a child CLIENT. */
@Entity
@Table(name = "SPCL_EDT")
@NamedQuery(
  name = SpecialEducation.FIND_BY_CLIENT_ID,
  query = "FROM SpecialEducation where childClientId =:" + SpecialEducation.PARAM_CLIENT_ID
)
public class SpecialEducation implements PersistentObject {

  public static final String PARAM_CLIENT_ID = "clientId";
  public static final String FIND_BY_CLIENT_ID = "SpecialEducation.findByClient";

  @Id
  @Column(name = "FKCHLD_CLT")
  private String childClientId;

  @Id
  @NotNull
  @Column(name = "THIRD_ID")
  private String thirdId;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Type(type = "yes_no")
  @Column(name = "SPC_ED_IND")
  private boolean specialEducationInd;

  @Column(name = "START_DT")
  private LocalDate startDate;

  @Override
  public Serializable getPrimaryKey() {
    return new SpecialEducationPK(thirdId, childClientId);
  }

  public String getChildClientId() {
    return childClientId;
  }

  public void setChildClientId(String childClientId) {
    this.childClientId = childClientId;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public boolean isSpecialEducationInd() {
    return specialEducationInd;
  }

  public void setSpecialEducationInd(boolean specialEducationInd) {
    this.specialEducationInd = specialEducationInd;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public static class SpecialEducationPK implements Serializable {

    private static final long serialVersionUID = -1338578310179366105L;

    @Id
    @Column(name = "THIRD_ID", nullable = false, length = 10)
    private String thirdId;

    @Id
    @Column(name = "FKCHLD_CLT", nullable = false, length = 10)
    private String childClientId;

    public SpecialEducationPK() {}

    public SpecialEducationPK(String thirdId, String childClientId) {
      this.thirdId = thirdId;
      this.childClientId = childClientId;
    }

    public String getThirdId() {
      return thirdId;
    }

    public void setThirdId(String thirdId) {
      this.thirdId = thirdId;
    }

    public String getChildClient() {
      return childClientId;
    }

    public void setChildClient(String childClientId) {
      this.childClientId = childClientId;
    }

    @Override
    public boolean equals(Object o) {
      return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
    }
  }
}
