package gov.ca.cwds.data.persistence;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.IdClass;

import org.apache.commons.lang3.builder.EqualsBuilder;

import gov.ca.cwds.data.persistence.cms.VarargPrimaryKey;

/**
 * Hibernate "embeddable" class for composite primary keys. For use in entity classes.
 * 
 * <h2>Entity Class Usage:</h2>
 * 
 * <h3>Add an EmbeddablePrimaryKey member to the entity class:</h3>
 *
 * <pre>
 * &#64;AttributeOverrides({
 *     &#64;AttributeOverride(name = "id1", column = @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)),
 *     &#64;AttributeOverride(name = "id2", column = @Column(name = "THIRD_ID", length = CMS_ID_LEN))})
 * &#64;EmbeddedId
 * private EmbeddablePrimaryKey id;
 * </pre>
 *
 * <h3>Create the primary key object in the entity constructors:</h3>
 *
 * <pre>
 * public OtherClientName() {
 *   super();
 *   this.id = new EmbeddablePrimaryKey();
 * }
 * 
 * public OtherClientName(String clientId, String firstName, String lastName, String middleName,
 * String namePrefixDescription, Short nameType, String suffixTitleDescription, String thirdId) {
 * super();
 * this.id = new EmbeddablePrimaryKey(clientId, thirdId);
 * this.firstName = firstName;
 * </pre>
 *
 * <h3>Create delegate getters/setters and set appropriate JSON field names on those methods:</h3>
 * 
 * <pre>
 * &#64;Override
 * public EmbeddablePrimaryKey getPrimaryKey() {
 *   return this.id;
 * }
 * 
 * &#64;JsonProperty(value = "clientId")
 * public String getClientId() {
 *   return StringUtils.trimToEmpty(id.getId1());
 * }
 * 
 * &#64;JsonProperty(value = "thirdId")
 * public String getThirdId() {
 *   return StringUtils.trimToEmpty(id.getId2());
 * }
 * 
 * &#64;JsonProperty(value = "clientId")
 * public void setClientId(String clientId) {
 *   id.setId1(clientId);
 * }
 * 
 * &#64;JsonProperty(value = "thirdId")
 * public void setThirdId(String thirdId) {
 *   id.setId2(thirdId);
 * }
 * </pre>
 *
 * <h3>Update equals and hashCode methods:</h3>
 *
 * <pre>
 *   &#64;Override
 *    public final int hashCode() {
 *      final int prime = 31;
 *      int ret = 1;
 *      ret = prime * ret + ((id == null || id.getId1() == null) ? 0 : id.getId1().hashCode());
 *      ret = prime * ret + ((id == null || id.getId2() == null) ? 0 : id.getId2().hashCode());
 *      ret = prime * ret + ((firstName == null) ? 0 : firstName.hashCode());
 * </pre>
 * 
 * <h2>Alternative Approaches:</h2>
 * 
 * <p>
 * Prefer the embeddable approach over Hibernate annotation {@link IdClass} due to usage
 * constraints. {@link IdClass} requires that members match the id columns of the parent class. From
 * the Javadoc of said annotation,
 * </p>
 * 
 * <blockquote> "The names of the fields or properties in the primary key class and the primary key
 * fields or properties of the entity must correspond and their types must be the same."
 * </blockquote>
 * 
 * <p>
 * Instead of {@link IdClass}, use the nifty approach below using {@link Embeddable} and
 * {@link EmbeddedId}. Try it on your friends!
 * </p>
 * 
 * @see VarargPrimaryKey
 */
@Embeddable
public final class EmbeddablePrimaryKey implements Serializable {

  /**
   * Base serialization version. Increment by version or create a unique number.
   */
  private static final long serialVersionUID = 1L;

  private String id1 = "";
  private String id2 = "";
  private String id3 = "";

  /**
   * Default constructor.
   */
  public EmbeddablePrimaryKey() {
    // Default, empty values for all key columns.
  }

  /**
   * Construct from arguments.
   * 
   * @param id1 generic id 1
   * @param id2 generic id 2
   */
  public EmbeddablePrimaryKey(String id1, String id2) {
    this.id1 = id1;
    this.id2 = id2;
  }

  /**
   * Construct from arguments.
   * 
   * @param id1 generic id 1
   * @param id2 generic id 2
   * @param id3 generic id 3
   */
  public EmbeddablePrimaryKey(String id1, String id2, String id3) {
    this.id1 = id1;
    this.id2 = id2;
    this.id3 = id3;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "embed_key__{" + id1.trim() + "_" + id2.trim() + "_" + id3.trim() + "}";
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id1 == null) ? 0 : id1.hashCode());
    result = prime * result + ((id2 == null) ? 0 : id2.hashCode());
    result = prime * result + ((id3 == null) ? 0 : id3.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    // Reduce cognitive complexity and eliminate human error -- at the expense of slightly slower
    // performance.
    return EqualsBuilder.reflectionEquals(this, obj, false);
  }

  /**
   * @return arbitrary id column, {@link #id1}.
   */
  public String getId1() {
    return id1;
  }

  /**
   * @param id1 arbitrary id column, {@link #id1}.
   */
  public void setId1(String id1) {
    this.id1 = id1;
  }

  /**
   * @return arbitrary id column, {@link #id2}.
   */
  public String getId2() {
    return id2;
  }

  /**
   * @param id2 arbitrary id column, {@link #id2}.
   */
  public void setId2(String id2) {
    this.id2 = id2;
  }

  /**
   * @return arbitrary id column, {@link #id3}.
   */
  public String getId3() {
    return id3;
  }

  /**
   * @param id3 arbitrary id column, {@link #id3}.
   */
  public void setId3(String id3) {
    this.id3 = id3;
  }
}
