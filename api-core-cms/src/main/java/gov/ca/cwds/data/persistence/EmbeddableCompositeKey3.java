package gov.ca.cwds.data.persistence;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.IdClass;

import gov.ca.cwds.data.persistence.cms.VarargPrimaryKey;

/**
 * Hibernate "embeddable" class for composite primary keys. For use in entity classes.
 * 
 * <h2>Entity Class Usage:</h2>
 * 
 * <h3>Add an EmbeddableCompositeKey3 member to the entity class:</h3>
 *
 * <pre>
 * &#64;AttributeOverrides({
 *     &#64;AttributeOverride(name = "id1", column = @Column(name = "FKCLIENT_T", length = CMS_ID_LEN)),
 *     &#64;AttributeOverride(name = "id2", column = @Column(name = "FKREFERL_T", length = CMS_ID_LEN)),
 *     &#64;AttributeOverride(name = "id3", column = @Column(name = "THIRD_ID", length = CMS_ID_LEN))})
 * &#64;EmbeddedId
 * private EmbeddableCompositeKey3 id;
 * </pre>
 *
 * <h3>Create the primary key object in the entity constructors:</h3>
 *
 * <pre>
 * public XYZEntity() {
 *   super();
 *   this.id = new EmbeddableCompositeKey3();
 * }
 * 
 * public XYZEntity(String clientId, String referralId, String firstName, String lastName, String middleName,
 * String namePrefixDescription, Short nameType, String suffixTitleDescription, String thirdId) {
 * super();
 * this.id = new EmbeddableCompositeKey3(clientId, referralId, thirdId);
 * this.firstName = firstName;
 * </pre>
 *
 * <h3>Create delegate getters/setters and set appropriate JSON field names on those methods:</h3>
 * 
 * <pre>
 * &#64;Override
 * public EmbeddableCompositeKey3 getPrimaryKey() {
 *   return this.id;
 * }
 * 
 * &#64;JsonProperty(value = "client_id")
 * public String getClientId() {
 *   return StringUtils.trimToEmpty(id.getId1());
 * }
 * 
 * &#64;JsonProperty(value = "referral_id")
 * public String getReferralId() {
 *   return StringUtils.trimToEmpty(id.getId2());
 * }
 * 
 * &#64;JsonProperty(value = "third_id")
 * public String getThirdId() {
 *   return StringUtils.trimToEmpty(id.getId3());
 * }
 * 
 * &#64;JsonProperty(value = "client_id")
 * public void setClientId(String clientId) {
 *   id.setId1(clientId);
 * }
 * 
 * &#64;JsonProperty(value = "referral_id")
 * public void setReferralId(String referralId) {
 *   id.setId2(referralId);
 * }
 * 
 * &#64;JsonProperty(value = "third_id")
 * public void setThirdId(String thirdId) {
 *   id.setId3(thirdId);
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
 *      ret = prime * ret + ((id == null || id.getId3() == null) ? 0 : id.getId3().hashCode());
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
public class EmbeddableCompositeKey3 extends EmbeddableCompositeKey2 {

  /**
   * Base serialization version. Increment by version or create a unique number.
   */
  private static final long serialVersionUID = 1L;

  private String id3 = "";

  /**
   * Default constructor.
   */
  public EmbeddableCompositeKey3() {
    // Default, empty values for all key columns.
  }

  /**
   * Construct from arguments.
   * 
   * @param id1 generic id 1
   * @param id2 generic id 2
   */
  public EmbeddableCompositeKey3(String id1, String id2) {
    super(id1, id2);
  }

  /**
   * Construct from arguments.
   * 
   * @param id1 generic id 1
   * @param id2 generic id 2
   * @param id3 generic id 3
   */
  public EmbeddableCompositeKey3(String id1, String id2, String id3) {
    super(id1, id2);
    this.id3 = id3;
  }

  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "embed_key3__{" + getId1().trim() + "_" + getId2().trim() + "_" + getId3().trim() + "}";
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
