package gov.ca.cwds.test.support;

import com.google.inject.Module;
import gov.ca.cwds.rest.BaseApiApplication;
import io.dropwizard.setup.Bootstrap;

/**
 * @author CWDS CALS API Team
 */

public class TestApplication extends BaseApiApplication<TestConfiguration> {
    @Override
    public Module applicationModule(Bootstrap<TestConfiguration> bootstrap) {
        return new ApplicationModule();
    }

}
