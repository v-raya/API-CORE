package gov.ca.cwds.drools;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.runtime.KieContainer;

/**
 * @author CWDS CALS API Team
 */
public final class DroolsKieContainerHolder {

  public static final DroolsKieContainerHolder INSTANCE = new DroolsKieContainerHolder();

  private volatile Map<String, KieContainer> containers = new HashMap<>();

  private DroolsKieContainerHolder() {}

  public synchronized KieContainer getKieContainer(String containerId) throws DroolsException {
    if (containers.get(containerId) == null) {
      final KieContainer newKieContainer =
          new DroolsKieContainerInstaller(containerId).installKieContainer();
      containers.put(containerId, newKieContainer);
    }
    return containers.get(containerId);
  }

}
