package gov.ca.cwds.test.support;

import com.google.inject.AbstractModule;
import gov.ca.cwds.inject.AuditingModule;

/**
 * Created by Alexander Serbin on 1/29/2018.
 */
public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new AuditingModule());
    }
}
