package gov.ca.cwds.data.legacy.cms.entity.converter;

/**
 * Created by TPT2 on 12/21/2017.
 */
public class ZipExtConverter extends BaseZipConverter<Short> {
  public ZipExtConverter() {
    super(4);
  }

  @Override
  Short valueOf(String s) {
    return Short.valueOf(s);
  }
}