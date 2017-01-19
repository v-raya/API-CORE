package gov.ca.cwds.data;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.StringWriter;
import java.util.BitSet;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import gov.ca.cwds.data.persistence.cms.CmsSystemCodeCacheService;
import gov.ca.cwds.data.persistence.cms.ISystemCodeCache;
import gov.ca.cwds.data.persistence.cms.SystemCodeDaoFileImpl;

public class CmsSystemCodeSerializerTest {

  private final static CmsSystemCodeCacheService CACHE =
      new CmsSystemCodeCacheService(new SystemCodeDaoFileImpl());

  private ObjectMapper objectMapper = new ObjectMapper();
  private SimpleModule module = new SimpleModule();
  private StringWriter stringWriter = new StringWriter();
  private CmsSystemCodeSerializer cmsSystemCodeSerializer;

  public void init(final CmsSystemCodeSerializer serializer) {
    SimpleModule module =
        new SimpleModule("SystemCodeModule", new Version(0, 1, 0, "cms_sys_code", "alpha", ""));
    module.addSerializer(Short.class, new CmsSystemCodeSerializer(CACHE));
    objectMapper.registerModule(module);
  }

  @Test
  public void type() throws Exception {
    assertThat(CmsSystemCodeSerializer.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    ISystemCodeCache cache = null;
    CmsSystemCodeSerializer target = new CmsSystemCodeSerializer(cache);
    assertThat(target, notNullValue());
  }

  @Test
  public void buildBits_Args$booleanArray() throws Exception {
    ISystemCodeCache cache = null;
    CmsSystemCodeSerializer target = new CmsSystemCodeSerializer(cache);
    // given
    boolean[] flags = new boolean[] {};
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    BitSet actual = target.buildBits(flags);
    // then
    // e.g. : verify(mocked).called();
    BitSet expected = new BitSet(0);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void buildSerializer_Args$ISystemCodeCache$boolean$boolean$boolean() throws Exception {
    ISystemCodeCache cache = null;
    CmsSystemCodeSerializer target = new CmsSystemCodeSerializer(cache);
    boolean showShortDescription = false;
    boolean showLogicalId = false;
    boolean showMetaCategory = false;
    CmsSystemCodeSerializer actual =
        target.buildSerializer(CACHE, showShortDescription, showLogicalId, showMetaCategory);
    CmsSystemCodeSerializer expected =
        new CmsSystemCodeSerializer(CACHE, showShortDescription, showLogicalId, showMetaCategory);
    assertThat(actual, is(equalTo(expected)));
  }

  @Test
  public void serialize_Args$Short$JsonGenerator$SerializerProvider() throws Exception {
    ISystemCodeCache cache = null;
    CmsSystemCodeSerializer target = new CmsSystemCodeSerializer(cache);
    // given
    Short s = null;
    JsonGenerator jgen = mock(JsonGenerator.class);
    SerializerProvider sp = mock(SerializerProvider.class);
    // e.g. : given(mocked.called()).willReturn(1);
    // when
    target.serialize(s, jgen, sp);
    // then
    // e.g. : verify(mocked).called();
  }

}
