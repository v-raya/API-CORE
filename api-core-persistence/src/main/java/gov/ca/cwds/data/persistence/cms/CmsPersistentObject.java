package gov.ca.cwds.data.persistence.cms;

import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.data.std.ApiObjectIdentity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.Type;


/**
 * Base class for objects in the legacy, DB2 persistence layer.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class CmsPersistentObject extends ApiObjectIdentity implements PersistentObject {

  /**
   * Baseline serialization version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * All legacy "identifier" fields and their foreign key are CHAR(10).
   */
  public static final int CMS_ID_LEN = 10;

  /**
   * Standard timestamp format for legacy DB2 tables.
   */
  protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

  @Column(name = "LST_UPD_ID")
  private String lastUpdatedId;

  @Type(type = "timestamp")
  @Column(name = "LST_UPD_TS")
  private Date lastUpdatedTime;

  /**
   * Default constructor.
   * 
   * Required for some framework calls.
   */
  protected CmsPersistentObject() {}

  /**
   * Constructor.
   * 
   * @param lastUpdatedId the id of the last person to update this object
   */
  protected CmsPersistentObject(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
    this.lastUpdatedTime = new Date();
  }

  /**
   * Constructor.
   * 
   * @param lastUpdatedId the id of the last person to update this object
   * @param lastUpdatedTime the time of the last person to update this object
   */
  protected CmsPersistentObject(String lastUpdatedId, Date lastUpdatedTime) {
    this.lastUpdatedId = lastUpdatedId;
    this.lastUpdatedTime = lastUpdatedTime;
  }

  /**
   * @return the lastUpdatedId
   */
  public String getLastUpdatedId() {
    return lastUpdatedId;
  }

  /**
   * @return the lastUpdatedTime
   */
  public Date getLastUpdatedTime() {
    return lastUpdatedTime;
  }

  /**
   * {@inheritDoc}
   * 
   * @see PersistentObject#getPrimaryKey()
   */
  @Override
  public abstract Serializable getPrimaryKey();

  @SuppressWarnings("javadoc")
  public void setLastUpdatedId(String lastUpdatedId) {
    this.lastUpdatedId = lastUpdatedId;
  }

  @SuppressWarnings("javadoc")
  public void setLastUpdatedTime(Date lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }

}
