package gov.ca.cwds.data.persistence.cms;

import gov.ca.cwds.data.persistence.PersistentObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.NamedQuery;


/**
 * {@link PersistentObject} representing a record from System Meta Table
 * 
 * @author CWDS API Team
 */
@NamedQuery(name = "gov.ca.cwds.data.persistence.cms.SystemMeta.findAll", query = "FROM SystemMeta")
@Entity
@Table(name = "S_META_T")
public class SystemMeta extends CmsPersistentObject {

  /**
   * Serialization version
   */
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "TBL_DSD_NM")
  @ColumnTransformer(read = "trim(TBL_DSD_NM)")
  private String logicalTableDsdName;

  @Column(name = "USR_TBL_NM")
  @ColumnTransformer(read = "trim(USR_TBL_NM)")
  private String userTableName;

  @Column(name = "SHT_DSC_NM")
  @ColumnTransformer(read = "trim(SHT_DSC_NM)")
  private String shortDescriptionName;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  public SystemMeta() {
    super();
  }

  /**
   * Construct from required fields.
   * 
   * @param logicalTableDsdName the Logical Table Dsd Name
   * @param userTableName the User Table Name
   * @param shortDescriptionName the Short Description Name
   */
  public SystemMeta(String logicalTableDsdName, String userTableName, String shortDescriptionName) {
    super();
    this.logicalTableDsdName = logicalTableDsdName;
    this.userTableName = userTableName;
    this.shortDescriptionName = shortDescriptionName;
  }

  /**
   * @return the logicalTableDsdName
   */
  public String getLogicalTableDsdName() {
    return logicalTableDsdName;
  }

  /**
   * @return the userTableName
   */
  public String getUserTableName() {
    return userTableName;
  }

  /**
   * @return the shortDescriptionName
   */
  public String getShortDescriptionName() {
    return shortDescriptionName;
  }

  /**
   * {@inheritDoc}
   * 
   * @see PersistentObject#getPrimaryKey()
   */
  @Override
  public String getPrimaryKey() {
    return getLogicalTableDsdName();
  }

}
