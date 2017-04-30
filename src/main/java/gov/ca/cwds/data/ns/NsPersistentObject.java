package gov.ca.cwds.data.ns;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

import gov.ca.cwds.data.persistence.PersistentObject;

/**
 * Base class for objects in the NS persistence layer.
 * 
 * <p>
 * Type P stands for the primary key type, which must extend interface {@link Serializable}.
 * </p>
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class NsPersistentObject implements PersistentObject {

  /**
   * Standard timestamp format for PostgreSQL persistence classes.
   */
  protected static final String TIMESTAMP_FORMAT = "yyyy-MM-dd-HH.mm.ss.SSS";

  @Column(name = "create_user_id")
  private String createUserId;

  @Type(type = "timestamp")
  @Column(name = "create_datetime")
  private Date createDateTime;

  @Column(name = "update_user_id")
  private String lastUpdatedId;

  @Type(type = "timestamp")
  @Column(name = "update_datetime")
  private Date lastUpdatedTime;

  /**
   * Default constructor
   * 
   * Required for Hibernate
   */
  protected NsPersistentObject() {
    // No-op.
  }

  /**
   * Constructor
   * 
   * @param lastUpdatedId the id of the last person to update this object
   * @param createUserId the id of the person created the record
   */
  protected NsPersistentObject(String lastUpdatedId, String createUserId) {
    this.lastUpdatedId = lastUpdatedId;
    this.lastUpdatedTime = new Date();
    this.createUserId = createUserId;
    this.createDateTime = new Date();
  }

  /**
   * @return the timestampFormat
   */
  public static String getTimestampFormat() {
    return TIMESTAMP_FORMAT;
  }

  /**
   * @return the createUserId
   */
  public String getCreateUserId() {
    return createUserId;
  }

  /**
   * @return the createDateTime
   */
  public Date getCreateDateTime() {
    return createDateTime;
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
   * @see gov.ca.cwds.data.persistence.PersistentObject#getPrimaryKey()
   */
  @Override
  public abstract Serializable getPrimaryKey();

}
