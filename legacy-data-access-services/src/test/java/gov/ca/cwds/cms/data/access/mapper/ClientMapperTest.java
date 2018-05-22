package gov.ca.cwds.cms.data.access.mapper;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.google.common.io.Resources;
import gov.ca.cwds.data.legacy.cms.entity.Client;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import gov.ca.cwds.data.persistence.cms.BaseClient;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

/** @author CWDS TPT-3 Team */
public class ClientMapperTest {

  private ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

  private static final String BASE_CLIENT_JSON = "fixture/base_client.json";
  private static final String LEGACY_CLIENT_JSON = "fixture/legacy_client.json";


  @Test
  public void toLegacyClient() throws Exception {
    BaseClient client = getBaseClient();
    Client legacyClient = getLegacyClient();
    Client mappedClient = clientMapper.toLegacyClient(client);
    assertEquals(legacyClient, mappedClient);
  }

  private BaseClient getBaseClient() throws IOException {
    String json =
        Resources.toString(Resources.getResource(BASE_CLIENT_JSON), StandardCharsets.UTF_8).trim();

    ObjectMapper mapper = new ObjectMapper()
      .registerModule(new ParameterNamesModule())
      .registerModule(new Jdk8Module())
      .registerModule(new JavaTimeModule());

    return mapper.readValue(json, TestClient.class);
  }

  private Client getLegacyClient() throws IOException {
    String json =
        Resources.toString(Resources.getResource(LEGACY_CLIENT_JSON), StandardCharsets.UTF_8)
            .trim();

    ObjectMapper mapper = new ObjectMapper()
      .registerModule(new ParameterNamesModule())
      .registerModule(new Jdk8Module())
      .registerModule(new JavaTimeModule());

    return mapper.readValue(json, Client.class);
  }

  private static class TestClient extends BaseClient {}
}
