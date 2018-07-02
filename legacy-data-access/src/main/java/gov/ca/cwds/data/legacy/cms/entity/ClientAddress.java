package gov.ca.cwds.data.legacy.cms.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.NamedQuery;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * {@link PersistentObject} representing a Client Address.
 * 
 * @author CWDS TPT-3 Team
 */
@NamedQuery(name = ClientAddress.NQ_FIND_BY_CLIENT_ID,
    query = "FROM gov.ca.cwds.data.legacy.cms.entity.ClientAddress WHERE fkClient = :"
        + ClientAddress.NQ_PARAM_CLIENT_ID)
@Entity
@Table(name = "CL_ADDRT")
public class ClientAddress extends BaseClientAddress {

  private static final long serialVersionUID = 1L;

  public static final String NQ_FIND_BY_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.ClientAddress.findByClientId";
  public static final String NQ_PARAM_CLIENT_ID = "clientId";

  @ManyToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "FKADDRS_T", nullable = false, insertable = false, updatable = false)
  private Address address;

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this, false);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

}
