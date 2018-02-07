package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQuery;

/** @author CWDS TPT-3 Team */
@Entity
@Table(name = "NR_FATLT")
@NamedQuery(name = NearFatality.NQ_BY_CLIENT_ID, query = "FROM NearFatality where clientId= :clientId")
public class NearFatality extends CmsPersistentObject {

  public static final String NQ_BY_CLIENT_ID = "NearFatality.findByClientId";

  @Column(name = "FKCLIENT_T", nullable = false)
  private String clientId;

  @Column(name = "NEAR_FT_DT")
  private LocalDateTime fatalityDate;

  @Id
  @Column(name = "THIRD_ID", nullable = false, length = 10)
  private String thirdId;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public LocalDateTime getFatalityDate() {
    return fatalityDate;
  }

  public void setFatalityDate(LocalDateTime fatalityDate) {
    this.fatalityDate = fatalityDate;
  }

  public String getThirdId() {
    return thirdId;
  }

  public void setThirdId(String thirdId) {
    this.thirdId = thirdId;
  }

  @Override
  public Serializable getPrimaryKey() {
    return getThirdId();
  }
}
