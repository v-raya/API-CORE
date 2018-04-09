package gov.ca.cwds.data.std;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import gov.ca.cwds.utils.JsonUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/** @author CWDS TPT-3 Team */
@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest({
  ToStringBuilder.class,
  HashCodeBuilder.class,
  JsonUtils.class,
  EqualsBuilder.class
})
public class ApiObjectIdentityTest {

  public static final String JSON_OBJECT = "{json:json}";
  private ApiObjectIdentity apiObjectIdentity;

  @Before
  public void init() throws JsonProcessingException {
    apiObjectIdentity =
        new ApiObjectIdentity() {
          @Override
          public String toString() {
            return super.toString();
          }

          @Override
          public int hashCode() {
            return super.hashCode();
          }

          @Override
          public boolean equals(Object obj) {
            return super.equals(obj);
          }

          @Override
          public String toJson() throws JsonProcessingException {
            return super.toJson();
          }
        };
    PowerMockito.mockStatic(ToStringBuilder.class);
    PowerMockito.mockStatic(HashCodeBuilder.class);
    PowerMockito.mockStatic(JsonUtils.class);
    PowerMockito.mockStatic(EqualsBuilder.class);

    when(ToStringBuilder.reflectionToString(
            any(ApiObjectIdentity.class), any(ToStringStyle.class), anyBoolean()))
        .thenReturn(JSON_OBJECT);
    when(HashCodeBuilder.reflectionHashCode(
            anyInt(), anyInt(), any(ApiObjectIdentity.class), anyBoolean(), any(), any()))
        .thenReturn(1);
    when(JsonUtils.to(any())).thenReturn(JSON_OBJECT);
    when(EqualsBuilder.reflectionEquals(
            any(ApiObjectIdentity.class), any(), anyBoolean(), any(), any()))
        .thenReturn(true);
  }

  @Test
  public void toStringTest() throws Exception {
    String str = apiObjectIdentity.toString();
    assertEquals(JSON_OBJECT, str);
  }

  @Test
  public void hashCodeTest() throws Exception {
    int hashCode = apiObjectIdentity.hashCode();
    assertEquals(1, hashCode);
  }

  @Test
  public void equalsTest() throws Exception {
    assertEquals(true, apiObjectIdentity.equals(new Object()));
  }

  @Test
  public void toJsoTest() throws Exception {
    String str = apiObjectIdentity.toJson();
    assertEquals(JSON_OBJECT, str);
  }
}
