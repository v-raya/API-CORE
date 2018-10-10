package gov.ca.cwds.dto.app;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.SortedMap;
import java.util.TreeMap;
import org.junit.Test;

public class SystemInformationDTOTest {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
    .ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  private ObjectMapper objectMapper = Jackson.newObjectMapper();

  @Test
  public void testSystemInformationDTO() throws IOException {
    SystemInformationDTO dto = new SystemInformationDTO();
    dto.setApplicationName("app1");
    dto.setVersion("1.0");
    dto.setBuildNumber("123");
    dto.setGitCommitHash("5fe64cdd988f8218b3568e886064be03cb990ae8");
    dto.setHealthStatus(true);

    SortedMap<String, HealthCheckResultDTO> healthCheckResultMap = new TreeMap<>();

    HealthCheckResultDTO checkResultDTO1 = new HealthCheckResultDTO();
    checkResultDTO1.setHealthy(true);
    checkResultDTO1.setMessage("test is healthy");
    LocalDateTime time1 = LocalDateTime.of(2018, 10, 10, 13, 30, 56);
    checkResultDTO1.setTimestamp(time1.format(DATE_TIME_FORMATTER));
    healthCheckResultMap.put("test_health", checkResultDTO1);

    HealthCheckResultDTO checkResultDTO2 = new HealthCheckResultDTO();
    checkResultDTO2.setHealthy(false);
    checkResultDTO2.setMessage("something is not healthy");
    LocalDateTime time2 = LocalDateTime.of(2018, 10, 10, 14, 42, 15);
    checkResultDTO2.setTimestamp(time2.format(DATE_TIME_FORMATTER));
    healthCheckResultMap.put("some_health", checkResultDTO2);

    dto.setHealthCheckResults(healthCheckResultMap);

    String actualJson = objectMapper.writeValueAsString(dto);
    String expectedJSON = fixture("dto/app/system-information.json");
    assertEquals(objectMapper.readTree(expectedJSON), objectMapper.readTree(actualJson));
  }
}
