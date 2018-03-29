package gov.ca.cwds.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import gov.ca.cwds.data.std.ApiMarker;
import gov.ca.cwds.rest.api.ApiException;

public class ApiFileAssistant implements ApiMarker {

  private static final long serialVersionUID = 1L;

  /**
   * Default constructor.
   */
  public ApiFileAssistant() {
    // no-op
  }

  /**
   * Convenient method to read a file into a String.
   * 
   * @param sourceFile source file
   * @return result String
   * @throws IOException on file read error
   */
  public String readFile(String sourceFile) throws IOException {
    String ret;
    try (final ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      IOUtils.copy(this.getClass().getResourceAsStream(sourceFile), out);
      out.flush();
      ret = out.toString();
    } finally {
      // auto-close.
    }

    return ret;
  }

  /**
   * Avoid path traversal vulnerabilities.
   * 
   * @param loc proposed file location
   * @return file to proposed location
   */
  public File validateFileLocation(String loc) {
    File ret;
    if (StringUtils.isNotBlank(loc)) {
      final String cleanLoc = loc.trim();
      if (cleanLoc.equals(FilenameUtils.normalize(cleanLoc))) {
        ret = new File(cleanLoc); // NOSONAR
        if (ret.exists()) {
          return ret;
        }
      }
    }

    throw new ApiException("PROHIBITED FILE LOCATION!");
  }

}
