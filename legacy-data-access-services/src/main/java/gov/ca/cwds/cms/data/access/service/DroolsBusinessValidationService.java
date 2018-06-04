//package gov.ca.cwds.cms.data.access.service;
//
//import com.google.inject.Inject;
//import gov.ca.cwds.cms.data.access.dto.BaseEntityAwareDTO;
//import gov.ca.cwds.drools.DroolsConfiguration;
//import gov.ca.cwds.drools.DroolsService;
//import gov.ca.cwds.rest.exception.BusinessValidationException;
//import gov.ca.cwds.rest.exception.IssueDetails;
//import gov.ca.cwds.security.realm.PerryAccount;
//import java.util.Set;
//
///**
// * @author CWDS TPT-3 Team
// */
//public class DroolsBusinessValidationService implements BusinessValidationService {
//
//  private final DroolsService droolsService;
//
//  @Inject
//  public DroolsBusinessValidationService(DroolsService droolsService) {
//    this.droolsService = droolsService;
//  }
//
//  @Override
//  public void runBusinessValidation(BaseEntityAwareDTO entityAwareDTO, PerryAccount principal,
//    DroolsConfiguration configuration) {
//    // Perform validation rules
//    Set<IssueDetails> detailsList =
//      droolsService.performBusinessRules(configuration, entityAwareDTO, principal);
//
//    if (!detailsList.isEmpty()) {
//      throw new BusinessValidationException(
//        "Business rules validation is failed for " + entityAwareDTO.getClass().getName(), detailsList);
//    }
//  }
//
//  @Override
//  public void runDataProcessing(BaseEntityAwareDTO entityAwareDTO, PerryAccount principal,
//    DroolsConfiguration configuration) {
//    droolsService.performBusinessRules(configuration, entityAwareDTO, principal);
//  }
//}