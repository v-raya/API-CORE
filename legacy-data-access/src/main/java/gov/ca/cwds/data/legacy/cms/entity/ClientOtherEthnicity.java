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
public class ClientOtherEthnicity extends OtherEthnicity {

  private static final long serialVersionUID = -2479439749768821267L;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "ESTBLSH_ID",
      referencedColumnName = "IDENTIFIER",
      insertable = false,
      updatable = false,
      nullable = false
  )
  private Client client;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ClientOtherEthnicity)) {
      return false;
    }
    ClientOtherEthnicity that = (ClientOtherEthnicity) o;
    return getEthnicityCode() == that.getEthnicityCode();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getEthnicityCode());
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
