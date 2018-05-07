package gov.ca.cwds.drools;

import static org.drools.compiler.kproject.models.KieModuleModelImpl.KMODULE_FILE_NAME;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;

/**
 * @author CWDS CALS API Team
 */

class DroolsKieContainerInstaller {

  private static final String AUTO_DISCOVERY_FILENAME = "auto-discovery";

  private final KieFileSystem kfs;
  private String containerId;

  DroolsKieContainerInstaller(String containerId) {
    this.containerId = containerId;
    this.kfs = KieServices.Factory.get().newKieFileSystem();
  }

  KieContainer installKieContainer() throws DroolsException {
    try {
      addKModuleXml();
      addDrlFiles();
      KieServices kieServices = KieServices.Factory.get();
      KieBuilder kieBuilder = kieServices.newKieBuilder(kfs);
      kieBuilder.buildAll();
      kieBuilder.getKieModule();
      if (!kieBuilder.getResults().getMessages(Message.Level.ERROR).isEmpty()) {
        throw new DroolsException(
            String.format(DroolsErrorMessages.DROOLS_CONTAINER_INSTALLATION_ERROR, containerId));
      }
      return kieServices
          .newKieContainer(containerId, kieServices.getRepository().getDefaultReleaseId());
    } catch (IOException e) {
      throw new DroolsException(
          String.format(DroolsErrorMessages.DROOLS_CONTAINER_INSTALLATION_ERROR, containerId), e);
    }
  }

  private void addDrlFiles() throws IOException {
    for (String resourceName : IOUtils
        .readLines(getResourceStream(AUTO_DISCOVERY_FILENAME), "UTF-8")) {
      if (resourceName.endsWith(".drl")) {
        kfs.write("src/main/resources/" + resourceName,
            IOUtils.toByteArray(getResourceStream(resourceName)));
      }
    }
  }

  private ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  private void addKModuleXml() throws IOException {
    InputStream inputStream = getResourceStream(KMODULE_FILE_NAME);
    kfs.writeKModuleXML(IOUtils.toByteArray(inputStream));
  }

  private InputStream getResourceStream(String resourceName) {
    return getClassLoader()
        .getResourceAsStream(containerId + "/" + resourceName);
  }

}
