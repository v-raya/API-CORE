package gov.ca.cwds.authorizer.drools;

import com.google.inject.Inject;
import gov.ca.cwds.authorizer.ClientCondition;
import gov.ca.cwds.authorizer.StaffPrivilegeType;
import gov.ca.cwds.authorizer.drools.configuration.ClientAuthorizationDroolsConfiguration;
import gov.ca.cwds.drools.DroolsException;
import gov.ca.cwds.drools.DroolsService;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author CWDS TPT-3 Team
 */
public class DroolsAuthorizationService {

  private final DroolsService droolsService;

  @Inject
  public DroolsAuthorizationService(DroolsService droolsService) {
    this.droolsService = droolsService;
  }

  public boolean authorizeClientRead(final ClientCondition clientCondition,
      final Collection<StaffPrivilegeType> staffPrivilegeTypes) throws DroolsException {
    final Collection<Object> facts = toFactsCollection(clientCondition, staffPrivilegeTypes);
    return droolsService.performAuthorizationRules(
        ClientAuthorizationDroolsConfiguration.INSTANCE,
        facts
    );
  }

  private Collection<Object> toFactsCollection(final ClientCondition clientCondition,
      final Collection<StaffPrivilegeType> staffPrivilegeTypes) {
    final Collection<Object> facts = new ArrayList<>(staffPrivilegeTypes.size() + 1);
    facts.addAll(staffPrivilegeTypes);
    facts.add(clientCondition);
    return facts;
  }

}
