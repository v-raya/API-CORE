package gov.ca.cwds.data.persistence.cms;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import gov.ca.cwds.rest.services.ServiceException;

public class FileSystemCodeDaoTest {

  @Test
  public void type() throws Exception {
    assertThat(FileSystemCodeDao.class, notNullValue());
  }

  @Test
  public void instantiation() throws Exception {
    FileSystemCodeDao target = new FileSystemCodeDao();
    assertThat(target, notNullValue());
  }

  @Test
  public void produce_Args$BufferedReader() throws Exception {
    FileSystemCodeDao target = new FileSystemCodeDao();
    BufferedReader reader = mock(BufferedReader.class);
    final List<CmsSystemCode> actual = target.produce(reader);
    final List<CmsSystemCode> expected = new ArrayList<>();
    assertThat(actual, is(equalTo(expected)));
  }

  @Test(expected = IOException.class)
  public void produce_Args$BufferedReader_T$IOException() throws Exception {
    FileSystemCodeDao target = new FileSystemCodeDao();
    BufferedReader reader = mock(BufferedReader.class);
    when(reader.readLine()).thenThrow(new IOException("stream broken"));
    target.produce(reader);
    fail("Expected exception was not thrown!");
  }

  @Test(expected = NumberFormatException.class)
  public void produce_Args$BufferedReader_T$_unparsable() throws Exception {
    FileSystemCodeDao target = new FileSystemCodeDao();
    BufferedReader reader = mock(BufferedReader.class);
    when(reader.readLine())
        .thenReturn("#7dkghalskdfj,,fhsdf\t,ala;sdfhdf\t\t\t\t\t\t,373ud;slahfhdkkd");
    target.produce(reader);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void produce_Args$File() throws Exception {
    FileSystemCodeDao target = new FileSystemCodeDao();
    final File file = new File(FileSystemCodeDao.class
        .getResource(target.getFileLocation().replaceAll("system_codes", "test_system_codes"))
        .getFile());
    final List<CmsSystemCode> actual = target.produce(file);
    final CmsSystemCode expected = new CmsSystemCode(4, "ABS_BPTC", "Arm", "25", "N", "", "", "");
    assertThat(actual, is(hasItem(expected)));
  }

  @Test(expected = ServiceException.class)
  public void produce_Args$File_T$ServiceException_NPE() throws Exception {
    FileSystemCodeDao target = new FileSystemCodeDao();
    File file = mock(File.class);
    target.produce(file);
    fail("Expected exception was not thrown!");
  }

  @Test
  public void produce_Args$() throws Exception {
    FileSystemCodeDao target = new FileSystemCodeDao();
    final List<CmsSystemCode> actual = target.produce();
    final CmsSystemCode expected = new CmsSystemCode(4, "ABS_BPTC", "Arm", "25", "N", "", "", "");
    assertThat(actual, is(hasItem(expected)));
  }

  @Test
  public void getAllSystemCodes_Args$() throws Exception {
    FileSystemCodeDao target = spy(new FileSystemCodeDao());
    final List<CmsSystemCode> actual = target.getAllSystemCodes();
    final CmsSystemCode expected = new CmsSystemCode(4, "ABS_BPTC", "Arm", "25", "N", "", "", "");
    assertThat(actual, is(hasItem(expected)));
  }

}
