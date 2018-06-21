package gov.ca.cwds.cms.data.access.dto;

import gov.ca.cwds.data.legacy.cms.entity.OtherPeopleScpRelationship;
import gov.ca.cwds.data.persistence.PersistentObject;
import java.util.ArrayList;
import java.util.List;

public abstract class RelationshipsAwareDTO<T extends PersistentObject> extends
  BaseEntityAwareDTO<T> {

  protected List<OtherPeopleScpRelationship> relationships = new ArrayList<>();

  public List<OtherPeopleScpRelationship> getRelationships() {
    return relationships;
  }

  public void setRelationships(
    List<OtherPeopleScpRelationship> relationships) {
    this.relationships = relationships;
  }

  public void addRelationship(OtherPeopleScpRelationship relationship) {
    relationships.add(relationship);
  }
}