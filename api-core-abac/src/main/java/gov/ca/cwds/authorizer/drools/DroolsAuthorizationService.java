package gov.ca.cwds.authorizer.drools;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.StaffPrivilegeType;
import gov.ca.cwds.authorizer.drools.configuration.DroolsAuthorizer;
import gov.ca.cwds.drools.DroolsService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  Drools authorization service.
 *
 * @author CWDS TPT-3 Team
 */
public class DroolsAuthorizationService {

  private final DroolsService droolsService;

  @Inject
  public DroolsAuthorizationService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }

  /**
   *  Authorization object operation.
   *
   * @param staffPrivilegeTypes staff privilage types.
   * @param droolsConfiguration drools configuration.
   * @param instances instances.
   * @return result of authorization.
   */
  public boolean authorizeObjectOperation(final Collection<StaffPrivilegeType> staffPrivilegeTypes,
      final DroolsAuthorizer droolsConfiguration, List<Object> instances) {
    final Collection<Object> facts = toFactsCollection(staffPrivilegeTypes, instances);
    return droolsService.performAuthorizationRules(
        droolsConfiguration.getInstance(),
        facts
    );
  }

  private Collection<Object> toFactsCollection(
      final Collection<StaffPrivilegeType> staffPrivilegeTypes, final List<Object> instances) {
    final Collection<Object> facts = new ArrayList<>(staffPrivilegeTypes.size() + instances.size());
    facts.addAll(staffPrivilegeTypes);
    facts.addAll(instances);
    return facts;
  }

}
