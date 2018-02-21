package gov.ca.cwds.data.legacy.cms.entity;

import static gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.FIND_TRIBAL_ANCESTRY_NOTIFICATION_BY_CHILD_CLIENT_ID;
import static gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.PARAM_CHILD_CLIENT_ID;

import gov.ca.cwds.data.legacy.cms.CmsPersistentObject;
import gov.ca.cwds.data.legacy.cms.entity.syscodes.County;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    query = "SELECT n FROM gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification n "
        + "where n.childClient.identifier =:" + PARAM_CHILD_CLIENT_ID
)
@Table(name = "TRBA_NOT")
public class TribalAncestryNotification extends CmsPersistentObject {

  private static final long serialVersionUID = 2723371565126423195L;

  public static final String FIND_TRIBAL_ANCESTRY_NOTIFICATION_BY_CHILD_CLIENT_ID =
      "gov.ca.cwds.data.legacy.cms.entity.TribalAncestryNotification.findByChildClientId";

  public static final String PARAM_CHILD_CLIENT_ID = "childClientId";

  @EmbeddedId
  private TribalAncestryNotification.Id id = new TribalAncestryNotification.Id();

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

  public TribalAncestryNotification.Id getId() {
    return id;
  }

  public void setId(TribalAncestryNotification.Id id) {
    this.id = id;
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

  @Embeddable
  public static class Id implements Serializable {

    private static final long serialVersionUID = -7055774987410564403L;

    @NotNull
    @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
    @Column(name = "THIRD_ID")
    private String thirdId;

    @NotNull
    @Size(min = CMS_ID_LEN, max = CMS_ID_LEN)
    @Column(name = "FKCHLD_CLT")
    private String childClientId;

    public Id() {
    }

    public Id(String childClientId, String thirdId) {
      this.childClientId = childClientId;
      this.thirdId = thirdId;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      TribalAncestryNotification.Id that = (TribalAncestryNotification.Id) o;
      return Objects.equals(childClientId, that.childClientId)
          && Objects.equals(thirdId, that.thirdId);
    }

    @Override
    public int hashCode() {
      return Objects.hash(childClientId, thirdId);
    }
  }
}
