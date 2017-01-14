package gov.ca.cwds.data.persistence.cms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.ca.cwds.rest.services.ServiceException;

/**
 * DAO retrieves all codes for {@link ISystemCodeCache} from a tab-delimited file.
 * 
 * @author CWDS API Team
 */
public class FileSystemCodeDao implements ISystemCodeDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemCodeDao.class);

  private String fileLocation =
      "/" + CmsSystemCodeCacheService.class.getPackage().getName().replace('.', '/') + '/'
          + "system_codes.tsv";

  /**
   * Default constructor.
   */
  public FileSystemCodeDao() {
    // Default, no-op.
  }

  /**
   * Produce the system cache facility from a reader.
   * 
   * @param reader reader of file, stream, whatever
   * @return initialized system cache
   * @throws IOException if unable to read
   */
  protected List<CmsSystemCode> produce(BufferedReader reader) throws IOException {
    List<CmsSystemCode> codes = new ArrayList<>();

    String line;
    while ((line = reader.readLine()) != null) {
      if (line.startsWith("SYS_ID")) {
        continue;
      }
      LOGGER.trace("line: {}", line);
      codes.add(CmsSystemCode.produce(line));
    }

    return codes;
  }

  /**
   * Convenience method. Produce the system cache facility from a file.
   * 
   * @param file source, delimited file
   * @return initialized system cache
   * @throws ServiceException if unable to parse the file
   */
  public List<CmsSystemCode> produce(File file) throws ServiceException {
    List<CmsSystemCode> ret;
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      ret = produce(reader);
    } catch (Exception e) {
      LOGGER.error("Unable to read system code file");
      throw new ServiceException("Unable to read system code file", e);
    }

    return ret;
  }

  /**
   * Convenience method. Produce the system cache facility from a file stored in this package.
   * 
   * @return initialized system cache
   * @throws ServiceException if unable to read the default system code file
   */
  protected List<CmsSystemCode> produce() throws ServiceException {
    LOGGER.info(FileSystemCodeDao.class.getPackage().getName());
    return produce(new File(FileSystemCodeDao.class.getResource(getFileLocation()).getFile()));
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
