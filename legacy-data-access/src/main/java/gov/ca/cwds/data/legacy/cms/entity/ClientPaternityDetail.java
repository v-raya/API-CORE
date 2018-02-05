package gov.ca.cwds.data.legacy.cms.entity;

import java.util.Objects;
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

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ClientPaternityDetail)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ClientPaternityDetail that = (ClientPaternityDetail) o;
    return Objects.equals(getClient(), that.getClient()) &&
        Objects.equals(getChildClient(), that.getChildClient()) &&
        Objects.equals(getPaternityTestDate(), that.getPaternityTestDate()) &&
        Objects
            .equals(getEstablishedLocationDescription(), that.getEstablishedLocationDescription());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(super.hashCode(), getClient(), getChildClient(), getPaternityTestDate(),
        getEstablishedLocationDescription());
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;

  }
}
