package gov.ca.cwds.data.legacy.cms;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Base class for objects in the legacy, DB2 persistence layer.
 *
 * @author CWDS API Team
 */
@SuppressWarnings("squid:S2160")
@MappedSuperclass
public abstract class CmsPersistentObjectVersioned extends CmsPersistentObjectBase {

  /**
   * LAST_UPDATE_TIMESTAMP - The date and time of the most recent update of an occurrence of this
   * entity type.
   */
  @Version
  @Column(name = "LST_UPD_TS", nullable = false)
  private Timestamp lastUpdatedTime;

  /**
   * @return the time and date of the most recent update to an occurrence of this entity type.
   */
  public Timestamp getLastUpdateTime() {
    return lastUpdatedTime == null ? null : (Timestamp) lastUpdatedTime.clone();
  }

  @SuppressWarnings("javadoc")
  public void setLastUpdateTime(Timestamp lastUpdatedTime) {
    this.lastUpdatedTime = (lastUpdatedTime == null ? null : (Timestamp) lastUpdatedTime.clone());
  }
}
