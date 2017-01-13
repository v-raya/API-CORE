package gov.ca.cwds.rest.api.persistence.cms;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.cms.CmsSystemCodeCache;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Test loader for the {@link CmsSystemCodeCache}. Use to verify updates to system codes.
 * 
 * @author CWDS API Team
 */
public class CmsSystemCodeCacheLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemCodeCache.class);

  public static void main(String[] args) {
    try {
      CmsSystemCodeCache cache;

      if (args.length > 1) {
        cache = CmsSystemCodeCache.produce(new File(args[0]));
      } else {
        cache = CmsSystemCodeCache.produce();
      }

    } catch (ServiceException e) {
      LOGGER.error("FATAL ERROR", e);
    }
  }

}
