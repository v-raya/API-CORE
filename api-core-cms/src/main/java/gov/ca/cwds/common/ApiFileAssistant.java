package gov.ca.cwds.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import gov.ca.cwds.data.std.ApiMarker;

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

}
