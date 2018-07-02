package gov.ca.cwds.data.persistence.cms;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.services.ServiceException;

/**
 * DAO retrieves all codes for {@link ApiSystemCodeCache} from a tab-delimited file. Convenient
 * where DB2 access is restricted or unavailable.
 * 
 * <p>
 * Update regularly with IBMA quarterly release.
 * </p>
 * 
 * @author CWDS API Team
 */
public class SystemCodeDaoFileImpl implements ApiSystemCodeDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(SystemCodeDaoFileImpl.class);

  private String fileLocation =
      "/" + SystemCodeDaoFileImpl.class.getPackage().getName().replace('.', '/') + '/'
          + "system_codes.tsv";

  /**
   * Default constructor.
   */
  public SystemCodeDaoFileImpl() {
    // Default, no-op.
  }

  /**
   * Produce the system cache facility from a reader.
   * 
   * @param reader reader of file, stream, whatever
   * @return initialized system cache
   * @throws IOException if unable to read
   */
  @SuppressWarnings("fb-contrib:PSC_PRESIZE_COLLECTIONS")
  protected List<CmsSystemCode> produce(BufferedReader reader) throws IOException {
    List<CmsSystemCode> codes = new ArrayList<>();

    String line;
    while ((line = reader.readLine()) != null) {
      if (line.startsWith("SYS_ID")) {
        continue;
      }

      codes.add(CmsSystemCode.produce(line));
    }

    return codes;
  }

  /**
   * Convenience method. Produce the system cache facility from a file.
   * 
   * @param iss delimited system code file as a stream
   * @return initialized system cache
   * @throws ServiceException if unable to parse the file
   */
  public List<CmsSystemCode> produce(InputStream iss) throws ServiceException {
    List<CmsSystemCode> ret;
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(iss))) {
      ret = produce(reader);
    } catch (Exception e) {
      LOGGER.error("Unable to read system code file");
      throw new ServiceException("Unable to read system code file stream", e);
    }

    return ret;
  }

  /**
   * Convenience method. Produce the system cache facility from a file stored in this package. Finds
   * the file from api-core.jar or from a file on the file system.
   * 
   * @return initialized system cache
   * @throws ServiceException if unable to read the default system code file
   */
  protected List<CmsSystemCode> produce() throws ServiceException {
    final String pkg = SystemCodeDaoFileImpl.class.getPackage().getName();
    LOGGER.info("package: {}", pkg);

    final String fileName =
        '/' + SystemCodeDaoFileImpl.class.getPackage().getName().replace('.', '/') + '/'
            + "system_codes.tsv";

    return produce(this.getClass().getResourceAsStream(fileName));
  }

  @Override
  public List<CmsSystemCode> getAllSystemCodes() {
    return this.produce();
  }

  /**
   * Get current file location.
   * 
   * @return file location.
   */
  public String getFileLocation() {
    return fileLocation;
  }

  /**
   * Set current file location.
   * 
   * @param fileLocation file location
   */
  public void setFileLocation(String fileLocation) {
    this.fileLocation = fileLocation;
  }

}
