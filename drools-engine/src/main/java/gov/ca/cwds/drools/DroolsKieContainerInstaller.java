package gov.ca.cwds.drools;

import static org.drools.compiler.kproject.models.KieModuleModelImpl.KMODULE_FILE_NAME;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;

/**
 * @author CWDS CALS API Team
 */

class DroolsKieContainerInstaller {

  private final KieFileSystem kfs;
  private String containerId;

  DroolsKieContainerInstaller(String containerId) {
    this.containerId = containerId;
    this.kfs = KieServices.Factory.get().newKieFileSystem();
  }

  KieContainer installKieContainer() throws DroolsException {
    try {
          KieServices kieServices = KieServices.Factory.get();
          addKModuleXml();
          addDrlFiles();
          KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
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

  private void addDrlFiles() {
    URL url = getClassLoader().getResource(containerId);
    File[] drls = new File(url.getPath())
        .listFiles(pathname -> pathname.getAbsolutePath().endsWith("drl"));
    Arrays.stream(drls).map(ResourceFactory::newFileResource).forEach(kfs::write);
  }

  private ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  private void addKModuleXml() throws IOException {
    InputStream inputStream = getClassLoader()
        .getResourceAsStream(containerId + "/" + KMODULE_FILE_NAME);
    kfs.writeKModuleXML(IOUtils.toByteArray(inputStream));
  }

}
