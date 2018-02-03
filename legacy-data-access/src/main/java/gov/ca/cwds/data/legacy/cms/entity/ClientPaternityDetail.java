package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@DiscriminatorValue(value = "C")
public class ClientPaternityDetail extends PaternityDetail {

  private static final long serialVersionUID = 873808060183392396L;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "PRMY_IDVID",
      referencedColumnName = "IDENTIFIER",
      insertable = false,
      updatable = false
  )
  private Client client;

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
