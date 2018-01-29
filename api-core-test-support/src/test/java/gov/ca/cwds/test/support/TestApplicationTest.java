package gov.ca.cwds.test.support;

import gov.ca.cwds.DataSourceName;
import liquibase.exception.LiquibaseException;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.assertEquals;

/**
 * Created by Alexander Serbin on 1/26/2018.
 */
public class TestApplicationTest extends BaseApiTest<TestConfiguration> {

    @ClassRule
    public static final BaseDropwizardApplication<TestConfiguration> application
            = new BaseDropwizardApplication<>(TestApplication.class, "config/test-api.yml");

    @Override
    protected BaseDropwizardApplication<TestConfiguration> getApplication() {
        return application;
    }

    @Before
    public void before() throws LiquibaseException {
        DatabaseHelper.setUpDatabase(application.getConfiguration().getFasDataSourceFactory(), DataSourceName.FAS);
        DatabaseHelper.setUpDatabase(application.getConfiguration().getCmsDataSourceFactory(), DataSourceName.CWS);
        DatabaseHelper.setUpDatabase(application.getConfiguration().getLisDataSourceFactory(), DataSourceName.LIS);
        DatabaseHelper.setUpDatabase(application.getConfiguration().getNsDataSourceFactory(), DataSourceName.NS);
        DatabaseHelper.setUpDatabase(application.getConfiguration().getCmsrsDataSourceFactory(), DataSourceName.CMSRS);
    }

    @Test
    public void test() {
        assertEquals("Ok", clientTestRule.target("test").
                request(MediaType.APPLICATION_JSON).get(String.class));
    }

}
