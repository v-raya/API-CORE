package gov.ca.cwds.rest.resources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.rest.SwaggerConfiguration;
import gov.ca.cwds.rest.views.SwaggerView;

import org.junit.Before;
import org.junit.Test;

public class SwaggerResourceTest {
  private static SwaggerConfiguration swaggerConfiguration = mock(SwaggerConfiguration.class);

  @Before
  public void setup() throws Exception {
    when(swaggerConfiguration.getTemplateName()).thenReturn("SwaggerResourceTest.template");
    when(swaggerConfiguration.getJsonUrl()).thenReturn("thejsonurl");
    when(swaggerConfiguration.getCallbackUrl()).thenReturn("thecallbackurl");
  }

  @Test
  public void getReturnsSwaggerViewWithCorrectUri() throws Exception {
    when(swaggerConfiguration.isShowSwagger()).thenReturn(true);
    SwaggerResource swaggerResource = new SwaggerResource(swaggerConfiguration);
    SwaggerView view = swaggerResource.get();
    assertThat(view.getCallbackUrl(), is("thecallbackurl"));
  }

  @Test
  public void getReturnsSwaggerViewWithJsonUrl() throws Exception {
    when(swaggerConfiguration.isShowSwagger()).thenReturn(true);
    SwaggerResource swaggerResource = new SwaggerResource(swaggerConfiguration);
    SwaggerView view = swaggerResource.get();
    assertThat(view.getJsonUrl(), is("thejsonurl"));
  }

  @Test
  public void getReturnsSwaggerViewWithCorrectTemplateName() throws Exception {
    when(swaggerConfiguration.isShowSwagger()).thenReturn(true);
    SwaggerResource swaggerResource = new SwaggerResource(swaggerConfiguration);
    SwaggerView view = swaggerResource.get();
    assertThat(view.getTemplateName(), is("/gov/ca/cwds/rest/views/SwaggerResourceTest.template"));
  }

}
