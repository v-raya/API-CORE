package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.SexualExploitationType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/** @author CWDS TPT-3 Team */
@Entity
@Table(name = "CSECHIST")
@NamedQuery(
    name = CsecHistory.FIND_BY_CLIENT_ID,
    query = "FROM gov.ca.cwds.data.legacy.cms.entity.CsecHistory where childClient =:" + CsecHistory.PARAM_CLIENT_ID
)
public class CsecHistory extends CmsPersistentObject {

  private static final long serialVersionUID = -1114099625983617913L;

  public static final String PARAM_CLIENT_ID = "clientId";
  public static final String FIND_BY_CLIENT_ID = "CsecHistory.findByClient";

  @Column(name = "CREATN_TS")
  private LocalDate creationDate;

  @Column(name = "START_DT")
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Column(name = "FKCHLD_CLT")
  private String childClient;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(name = "CSEC_TPC", referencedColumnName = "SYS_ID")
  private SexualExploitationType sexualExploitationType;

  @Id
  @Column(name = "THIRD_ID")
  private String thirdId;

  @Override
  public Serializable getPrimaryKey() {
    return getThirdId();
  }

  public LocalDate getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getChildClient() {
    return childClient;
  }

  public void setChildClient(String childClient) {
    this.childClient = childClient;
  }

  public SexualExploitationType getSexualExploitationType() {
    return sexualExploitationType;
  }

  public void setSexualExploitationType(SexualExploitationType sexualExploitationType) {
    this.sexualExploitationType = sexualExploitationType;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }
}
