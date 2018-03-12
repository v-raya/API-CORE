package gov.ca.cwds.cms.data.access.service;

import com.google.inject.Inject;
import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
import gov.ca.cwds.data.persistence.PersistentObject;
import gov.ca.cwds.drools.DroolsConfiguration;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.rest.exception.BusinessValidationException;
import gov.ca.cwds.rest.exception.IssueDetails;
import gov.ca.cwds.security.realm.PerryAccount;
import java.util.Set;

/** @author CWDS TPT-3 Team */
public class BusinessValidationService<
    T extends PersistentObject, P extends BaseEntityAwareDTO<T>> {

  private final DroolsService droolsService;

  @Inject
  public BusinessValidationService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }

  public void runBusinessValidation(
      P entityAwareDTO, PerryAccount principal, DroolsConfiguration configuration)
      throws DroolsException {
    // Perform validation rules
    Set<IssueDetails> detailsList =
        droolsService.performBusinessRules(configuration, entityAwareDTO, principal);

    if (!detailsList.isEmpty()) {
      throw new BusinessValidationException(
          "Business rules validation is failed for " + entityAwareDTO.getClass().getName(), detailsList);
    }
  }

  public void runDataProcessing(
      P entityAwareDTO, PerryAccount principal, DroolsConfiguration configuration)
      throws DroolsException {
    droolsService.performBusinessRules(configuration, entityAwareDTO, principal);
  }
}
