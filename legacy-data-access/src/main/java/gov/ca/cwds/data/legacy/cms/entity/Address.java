package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.persistence.PersistentObject;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * {@link PersistentObject} representing an Address
 * 
 * @author CWDS TPT-3 Team
 */
@Entity
@Table(name = "ADDRS_T")
public class Address extends BaseAddress {

  private static final long serialVersionUID = 2560043972446866951L;

}
