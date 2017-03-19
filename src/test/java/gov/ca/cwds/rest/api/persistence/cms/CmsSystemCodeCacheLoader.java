package gov.ca.cwds.rest.api.persistence.cms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.data.persistence.cms.CmsSystemCodeCacheService;
import gov.ca.cwds.data.persistence.cms.SystemCodeDaoFileImpl;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeCache;
import gov.ca.cwds.data.persistence.cms.ApiSystemCodeDao;
import gov.ca.cwds.rest.services.ServiceException;

/**
 * Test loader for the {@link CmsSystemCodeCacheService}. Use to verify updates to system codes.
 * 
 * @author CWDS API Team
 */
public class CmsSystemCodeCacheLoader {

  private static final Logger LOGGER = LoggerFactory.getLogger(CmsSystemCodeCacheService.class);

  public static void main(String[] args) {
    try {
      ApiSystemCodeDao dao = new SystemCodeDaoFileImpl();

      if (args.length > 1) {
        // dao = new FileSystemCodeDao(new File(args[0]));
      }

      ApiSystemCodeCache cache = new CmsSystemCodeCacheService(dao);

    } catch (ServiceException e) {
      LOGGER.error("FATAL ERROR", e);
    }
  }

}
