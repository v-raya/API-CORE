package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.FIND_TRIBAL_ANCESTRY_NOTIFICATION_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.PARAM_CHILD_CLIENT_ID;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@SuppressWarnings({"squid:S3437", "squid:S2160"})
@Entity
@NamedQuery(
    name = FIND_TRIBAL_ANCESTRY_NOTIFICATION_BY_CHILD_CLIENT_ID,
    query = "SELECT n FROM TribalAncestryNotification n where n.childClient.identifier =:"
        + PARAM_CHILD_CLIENT_ID
)
@Table(name = "TRBA_NOT")
public class TribalAncestryNotification extends CmsPersistentObject {

  private static final long serialVersionUID = 3860004593026707422L;

  public static final String FIND_TRIBAL_ANCESTRY_NOTIFICATION_BY_CHILD_CLIENT_ID =
      "TribalAncestryNotification.findTribalAncestryNotificationByChildClientId";

  public static final String PARAM_CHILD_CLIENT_ID = "childClientId";

  @Id
  @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
  @Column(name = "THIRD_ID")
  private String id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "FKCHLD_CLT",
      referencedColumnName = "FKCLIENT_T",
      insertable = false,
      updatable = false
  )
  private ChildClient childClient;

  @ManyToOne(fetch = FetchType.LAZY)
  @Fetch(FetchMode.SELECT)
  @JoinColumn(
      name = "GVR_ENTC",
      referencedColumnName = "SYS_ID",
      insertable = false,
      updatable = false
  )
  private County county;

  @NotNull
  @Column(name = "INFORM_DT")
  private LocalDate notificationDate;

  @Override
  public Serializable getPrimaryKey() {
    return getId();
  }

  public String getId() {
    return id;
  }

  public void setId(String thirdId) {
    this.id = thirdId;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  public LocalDate getNotificationDate() {
    return notificationDate;
  }

  public void setNotificationDate(LocalDate notificationDate) {
    this.notificationDate = notificationDate;
  }

  public ChildClient getChildClient() {
    return childClient;
  }

  public void setChildClient(ChildClient childClient) {
    this.childClient = childClient;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TribalAncestryNotification)) {
      return false;
    }
    TribalAncestryNotification that = (TribalAncestryNotification) o;
    return Objects.equals(getChildClient(), that.getChildClient()) &&
        Objects.equals(getCounty(), that.getCounty()) &&
        Objects.equals(getNotificationDate(), that.getNotificationDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getChildClient(), getCounty(), getNotificationDate());
  }
}
