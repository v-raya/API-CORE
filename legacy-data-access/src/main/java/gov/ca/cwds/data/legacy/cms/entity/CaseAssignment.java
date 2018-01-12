package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.persistence.PersistentObject;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * {@link PersistentObject} representing an Assignment for {@link Case} entity
 *
 * @author CWDS TPT-3 Team
 */
@Entity
@DiscriminatorValue(value = "C")
public class CaseAssignment extends BaseAssignment {

  private static final long serialVersionUID = -2902356846202575651L;

  @NotFound(action = NotFoundAction.IGNORE)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "ESTBLSH_ID",
      referencedColumnName = "IDENTIFIER",
      insertable = false,
      updatable = false
  )
  private Case theCase;

  public Case getTheCase() {
    return theCase;
  }

  public void setTheCase(Case theCase) {
    this.theCase = theCase;
  }

}
