package gov.ca.cwds.rest.api.persistence.cms;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.cms.CmsSystemCodeCache;

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

    } catch (IOException e) {
      LOGGER.error("FATAL ERROR", e);
    }
  }

}
