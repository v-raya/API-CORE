package gov.ca.cwds.test.support;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

/**
 * @author CWDS CALS API Team
 */

public class TestApplication extends Application<TestConfiguration> {

    @Override
    public void run(TestConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new TestResource());
    }

}
