package gov.ca.cwds.test.support;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.client.Client;

/**
 * Created by Alexander Serbin on 1/26/2018.
 */
public class BaseDropwizardApplication<T  extends Configuration> extends DropwizardAppRule<T>  {

    public BaseDropwizardApplication(Class<? extends Application<T>> applicationClass, String configPath) {
        super(applicationClass, ResourceHelpers.resourceFilePath(configPath));
    }

    @Override
    public Client client() {
        Client client = super.client();
        if (((JerseyClient) client).isClosed()) {
            client = clientBuilder().build();
        }
        return client;
    }

}
