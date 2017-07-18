package gov.ca.cwds.data.es;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.ca.cwds.data.std.ApiObjectIdentity;

/**
 * Relationship.
 * 
 * @author CWDS API Team
 */
public class ElasticSearchPersonRelationship extends ApiObjectIdentity {

  /**
   * Default serialization version.
   */
  private static final long serialVersionUID = 1L;

  @JsonProperty("related_person_id")
  private String relatedPersonId;

  @JsonProperty("related_person_first_name")
  private String relatedPersonFirstName;

  @JsonProperty("related_person_last_name")
  private String relatedPersonLastName;

  @JsonProperty("indexed_person_relationship")
  private String indexedPersonRelationship;

  @JsonProperty("related_person_legacy_id")
  @Deprecated
  private String relatedPersonLegacyId;

  @JsonProperty("related_person_legacy_source_table")
  @Deprecated
  private String relatedPersonLegacySourceTable;

  @JsonProperty("related_person_relationship")
  private String relatedPersonRelationship;

  @JsonProperty("relationship_context")
  private String relationshipContext;

  @JsonProperty("legacy_descriptor")
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ElasticSearchLegacyDescriptor legacyDescriptor = new ElasticSearchLegacyDescriptor();

  @JsonIgnore
  @SuppressWarnings("javadoc")
  public String getRelatedPersonId() {
    return relatedPersonId;
  }

  @SuppressWarnings("javadoc")
  public void setRelatedPersonId(String relatedPersonId) {
    this.relatedPersonId = relatedPersonId;
  }

  @JsonIgnore
  @SuppressWarnings("javadoc")
  public String getRelatedPersonFirstName() {
    return relatedPersonFirstName;
  }

  @SuppressWarnings("javadoc")
  public void setRelatedPersonFirstName(String relatedPersonFirstName) {
    this.relatedPersonFirstName = relatedPersonFirstName;
  }

  @JsonIgnore
  @SuppressWarnings("javadoc")
  public String getRelatedPersonLastName() {
    return relatedPersonLastName;
  }

  @SuppressWarnings("javadoc")
  public void setRelatedPersonLastName(String relatedPersonLastName) {
    this.relatedPersonLastName = relatedPersonLastName;
  }

  @JsonIgnore
  @SuppressWarnings("javadoc")
  public String getIndexedPersonRelationship() {
    return indexedPersonRelationship;
  }

  @SuppressWarnings("javadoc")
  public void setIndexedPersonRelationship(String indexedPersonRelationship) {
    this.indexedPersonRelationship = indexedPersonRelationship;
  }

  @Deprecated
  @SuppressWarnings("javadoc")
  public String getRelatedPersonLegacyId() {
    return relatedPersonLegacyId;
  }

  @Deprecated
  public void setRelatedPersonLegacyId(String relatedPersonLegacyId) {
    this.relatedPersonLegacyId = relatedPersonLegacyId;
  }

  @Deprecated
  public String getRelatedPersonLegacySourceTable() {
    return relatedPersonLegacySourceTable;
  }

  @Deprecated
  public void setRelatedPersonLegacySourceTable(String relatedPersonLegacySourceTable) {
    this.relatedPersonLegacySourceTable = relatedPersonLegacySourceTable;
  }

  public String getRelatedPersonRelationship() {
    return relatedPersonRelationship;
  }

  public void setRelatedPersonRelationship(String relatedPersonRelationship) {
    this.relatedPersonRelationship = relatedPersonRelationship;
  }

  public String getRelationshipContext() {
    return relationshipContext;
  }

  public void setRelationshipContext(String relationshipContext) {
    this.relationshipContext = relationshipContext;
  }

  public ElasticSearchLegacyDescriptor getLegacyDescriptor() {
    return legacyDescriptor;
  }

  public void setLegacyDescriptor(ElasticSearchLegacyDescriptor legacyDescriptor) {
    this.legacyDescriptor = legacyDescriptor;
  }
}