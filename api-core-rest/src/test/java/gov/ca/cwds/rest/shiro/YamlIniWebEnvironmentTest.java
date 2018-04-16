package gov.ca.cwds.rest.shiro;

import gov.ca.cwds.rest.YamlShiroConfiguration;
import org.apache.shiro.config.Ini;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class YamlIniWebEnvironmentTest {
  @Test
  public void testPatch() {
    YamlShiroConfiguration yamlShiroConfiguration = new YamlShiroConfiguration();
    setSectionProperty(yamlShiroConfiguration, "section", "yml.prop.name", "yml.prop.value1");
    setSectionProperty(yamlShiroConfiguration, "section", "prop.name", "yml.prop.value2");
    setSectionProperty(yamlShiroConfiguration, "section2", "prop.name", "yml.prop.value3");

    YamlIniWebEnvironment yamlIniWebEnvironment = new YamlIniWebEnvironment(yamlShiroConfiguration);
    Ini ini = new Ini();
    ini.setSectionProperty("section", "prop.name", "prop.value");
    yamlIniWebEnvironment.setIni(ini);
    yamlIniWebEnvironment.configure();
    Assert.assertEquals(2, ini.getSectionNames().size());
    Assert.assertEquals(2, ini.getSection("section").size());
    Assert.assertEquals(1, ini.getSection("section2").size());
    Assert.assertEquals("yml.prop.value1", ini.getSection("section").get("yml.prop.name"));
    Assert.assertEquals("yml.prop.value2", ini.getSection("section").get("prop.name"));
    Assert.assertEquals("yml.prop.value3", ini.getSection("section2").get("prop.name"));
  }

  private void setSectionProperty(YamlShiroConfiguration yamlShiroConfiguration, String section, String key, String value) {
    Map<String, Map<String, String>> yamlIni = Optional.ofNullable(yamlShiroConfiguration.getIni()).orElseGet(() -> {
      yamlShiroConfiguration.setIni(new HashMap<>());
      return yamlShiroConfiguration.getIni();
    });
    if(!yamlIni.containsKey(section)) {
      yamlIni.put(section, new HashMap<>());
    }
    yamlIni.get(section).put(key, value);

  }
}
