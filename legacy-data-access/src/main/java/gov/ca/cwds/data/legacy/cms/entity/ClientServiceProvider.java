package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider.FIND_BY_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider.PARAM_CLIENT_ID;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@NamedQuery(
    name = FIND_BY_CLIENT_ID,
    query = "SELECT csp FROM gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider csp "
        + "where csp.client.identifier =:" + PARAM_CLIENT_ID
)
@Table(name = "CSRVPR_T")
public class ClientServiceProvider extends CmsPersistentObject {

  private static final long serialVersionUID = 8252945680365006950L;

  public static final String FIND_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.ClientServiceProvider.findByClientId";

  public static final String PARAM_CLIENT_ID = "clientId";

  @Id
  @NotNull
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @Column(name = "THIRD_ID")
  private String id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "FKCLIENT_T",
      referencedColumnName = "IDENTIFIER",
      insertable = false,
      updatable = false
  )
  private Client client;

  @NotNull
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @Column(name = "FKSVC_PVRT")
  private String serviceProviderId;

  @NotNull
  @Size(max = 254)
  @ColumnTransformer(read = "trim(DESCR)")
  @Column(name = "DESCR")
  private String description;

  @NotNull
  @Column(name = "START_DT")
  private LocalDate startDate;

  @Column(name = "END_DT")
  private LocalDate endDate;

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String thirdId) {
    this.id = thirdId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String descr) {
    this.description = descr;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public String getServiceProviderId() {
    return serviceProviderId;
  }

  public void setServiceProviderId(String fksvcPvrt) {
    this.serviceProviderId = fksvcPvrt;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ClientServiceProvider)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ClientServiceProvider that = (ClientServiceProvider) o;
    return Objects.equals(getClientId(getClient()), getClientId(that.getClient())) &&
        Objects.equals(getServiceProviderId(), that.getServiceProviderId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getClientId(getClient()), getServiceProviderId());
  }

  private static String getClientId(Client client) {
    return  client == null ? null : client.getIdentifier();
  }
}
