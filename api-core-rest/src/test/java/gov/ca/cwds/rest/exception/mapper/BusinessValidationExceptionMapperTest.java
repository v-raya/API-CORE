package gov.ca.cwds.rest.exception.mapper;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.exception.IssueDetails;

public class BusinessValidationExceptionMapperTest {

  @Test
  public void testToResponse() throws Exception {
    BusinessValidationExceptionMapper mapper = new BusinessValidationExceptionMapper();

    Set<IssueDetails> issues = new HashSet<>();
    IssueDetails issueDetails = new IssueDetails();
    issues.add(issueDetails);

    BusinessValidationException exception = new BusinessValidationException(issues);

    javax.ws.rs.core.Response response = mapper.toResponse(exception);

    Assert.assertEquals(422, response.getStatus());
  }

}
