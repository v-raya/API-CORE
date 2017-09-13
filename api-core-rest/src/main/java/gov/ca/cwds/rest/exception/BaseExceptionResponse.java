package gov.ca.cwds.rest.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.io.Serializable;
import java.util.Set;

/**
 * @author CWDS CALS API Team
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BaseExceptionResponse implements Serializable {

  private Set<IssueDetails> issueDetails;

  public Set<IssueDetails> getIssueDetails() {
    return issueDetails;
  }

  public void setIssueDetails(Set<IssueDetails> issueDetails) {
    this.issueDetails = issueDetails;
  }

}
