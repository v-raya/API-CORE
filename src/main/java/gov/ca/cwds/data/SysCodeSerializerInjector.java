package gov.ca.cwds.data;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.inject.Inject;

/**
 * Registers custom Jackson serializer, {@link CmsSystemCodeSerializer}, with Jackson.
 * 
 * <p>
 * Add post-injection initialization to this class.
 * </p>
 * 
 * @author CWDS API Team
 * @see CmsSystemCodeSerializer
 */
public class SysCodeSerializerInjector {

  /**
   * Constructor.
   * 
   * <p>
   * Guice finds and injects the dependencies for us.
   * </p>
   * 
   * @param sysCodeSerializer our Jackson serializer
   * @param om the common ObjectMapper registered with this application
   */
  @Inject
  public SysCodeSerializerInjector(final CmsSystemCodeSerializer sysCodeSerializer,
      final ObjectMapper om) {
    SimpleModule module =
        new SimpleModule("SystemCodeModule", new Version(0, 1, 0, "cms_sys_code", "alpha", ""));
    module.addSerializer(Short.class, sysCodeSerializer);
    om.registerModule(module);
  }

}
