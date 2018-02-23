package gov.ca.cwds.cms.data.access.service.impl.substituteCareProvider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import gov.ca.cwds.cms.data.access.CWSIdentifier;
import gov.ca.cwds.cms.data.access.dao.ClientScpEthnicityDao;
import gov.ca.cwds.cms.data.access.dao.CountyOwnershipDao;
import gov.ca.cwds.cms.data.access.dao.PlacementHomeInformationDao;
import gov.ca.cwds.cms.data.access.dao.SsaName3Dao;
import gov.ca.cwds.cms.data.access.dao.SubstituteCareProviderDao;
import gov.ca.cwds.cms.data.access.dao.SubstituteCareProviderUcDao;
import gov.ca.cwds.cms.data.access.dto.SCPEntityAwareDTO;
import gov.ca.cwds.cms.data.access.mapper.CountyOwnershipMapper;
import gov.ca.cwds.cms.data.access.service.impl.IdGenerator;
import gov.ca.cwds.cms.data.access.service.impl.SubstituteCareProviderServiceImpl;
import gov.ca.cwds.data.legacy.cms.entity.SubstituteCareProvider;
import gov.ca.cwds.drools.DroolsService;
import gov.ca.cwds.security.utils.PrincipalUtils;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by TPT2 on 12/11/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({IdGenerator.class, PrincipalUtils.class})
public class R07045Test {

  private SubstituteCareProviderServiceImpl scpService;
  private ClientScpEthnicityDao mockedEthnicityDao;
  @Before
  public void setup() {
    scpService = new SubstituteCareProviderServiceImpl();
    mockedEthnicityDao = mock(ClientScpEthnicityDao.class);
    scpService.setClientScpEthnicityDao(mockedEthnicityDao);
    scpService.setDroolsService(mock(DroolsService.class));
    scpService.setSubstituteCareProviderDao(mock(SubstituteCareProviderDao.class));
    scpService.setSubstituteCareProviderUcDao(mock(SubstituteCareProviderUcDao.class));
    scpService.setPlacementHomeInformationDao(mock(PlacementHomeInformationDao.class));
    scpService.setCountyOwnershipMapper(CountyOwnershipMapper.INSTANCE);
    scpService.setCountyOwnershipDao(mock(CountyOwnershipDao.class));
    scpService.setScpSsaName3Dao(mock(SsaName3Dao.class));
  }

  @Test
  public void testRule() throws Exception {
    PowerMockito.mockStatic(IdGenerator.class);
    when(IdGenerator.generateId()).thenReturn("1q2w3e4r");
    PowerMockito.mockStatic(PrincipalUtils.class);
    when(PrincipalUtils.getStaffPersonId()).thenReturn("0X5");
    SCPEntityAwareDTO scpEntityAwareDTO = new SCPEntityAwareDTO();
    scpEntityAwareDTO.setEntity(new SubstituteCareProvider());

    //2 ethnicity was added
    scpEntityAwareDTO.setEthnicityList(Arrays.asList(mock(CWSIdentifier.class), mock(CWSIdentifier.class)));

    SubstituteCareProvider substituteCareProvider = scpService.create(scpEntityAwareDTO);

    //expecting 2 ethnicity to
    verify(mockedEthnicityDao, times(2)).create(any());
  }

}
