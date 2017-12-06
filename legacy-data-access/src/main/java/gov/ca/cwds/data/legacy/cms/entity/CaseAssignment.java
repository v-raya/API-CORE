package gov.ca.cwds.data.legacy.cms.entity;

import gov.ca.cwds.data.persistence.PersistentObject;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ESTBLSH_ID", referencedColumnName = "IDENTIFIER", insertable=false, updatable=false)
  private Case theCase;

  public Case getTheCase() {
    return theCase;
  }

  public void setTheCase(Case theCase) {
    this.theCase = theCase;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof CaseAssignment)) {
      return false;
    }

    CaseAssignment that = (CaseAssignment) o;

    return new EqualsBuilder()
        .appendSuper(super.equals(o))
        .append(theCase, that.theCase)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .appendSuper(super.hashCode())
        .append(theCase)
        .toHashCode();
  }
}
