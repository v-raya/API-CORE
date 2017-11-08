package gov.ca.cwds.data.persistence;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;


/**
 * Base class for objects in the legacy, DB2 persistence layer.
 * 
 * @author CWDS API Team
 */
@MappedSuperclass
public abstract class CmsPersistentObject implements PersistentObject {

  /**
   * Baseline serialization version.
   */
  private static final long serialVersionUID = 1L;

  /**
   * All legacy "identifier" fields and their foreign key are CHAR(10).
   */
  public static final int CMS_ID_LEN = 10;

  @Column(name = "LST_UPD_ID", nullable = false, length = 3)
   private String lastUpdatedId;

  @Column(name = "LST_UPD_TS", nullable = false)
  private LocalDateTime lastUpdatedTime;

  /**
   * @return the lastUpdatedId
   */
  public String getLastUpdateId() {
    return lastUpdatedId;
  }

  /**
   * @return the lastUpdatedTime
   */
  public LocalDateTime getLastUpdateTime() {
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
  public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
    this.lastUpdatedTime = lastUpdatedTime;
  }
}
