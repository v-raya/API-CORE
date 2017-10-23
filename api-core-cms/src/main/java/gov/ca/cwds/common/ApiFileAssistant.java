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

  public ApiFileAssistant() {
    // no-op
  }

  public String readFile(String sourceFile) throws IOException {
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    IOUtils.copy(this.getClass().getResourceAsStream(sourceFile), out);
    out.flush();
    return out.toString();
  }

  /**
   * Deter path traversal vulnerabilities.
   * 
   * @param loc proposed file location
   * @return file to proposed location
   */
  public File validateFileLocation(String loc) {
    File ret = null;
    if (StringUtils.isNotBlank(loc)) {
      final String cleanLoc =
          loc.lastIndexOf('/') > -1 ? loc.substring(0, loc.lastIndexOf('/')) : loc;
      if (cleanLoc.equals(FilenameUtils.normalize(cleanLoc))) {
        ret = new File(cleanLoc);
        if (ret.exists()) {
          return ret;
        }
      }
    }

    if (ret != null) {
      return ret;
    } else {
      throw new ApiException("PROHIBITED FILE LOCATION!");
    }
  }

}
