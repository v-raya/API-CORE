package gov.ca.cwds.test.support;

import gov.ca.cwds.rest.MinimalApiConfiguration;
import io.dropwizard.db.DataSourceFactory;

/**
 * Created by Alexander Serbin on 1/26/2018.
 */
public class TestConfiguration extends MinimalApiConfiguration {

    private DataSourceFactory fasDataSourceFactory;
    private DataSourceFactory lisDataSourceFactory;
    private DataSourceFactory nsDataSourceFactory;
    private DataSourceFactory cmsDataSourceFactory;
    private DataSourceFactory cmsrsDataSourceFactory;

    public DataSourceFactory getFasDataSourceFactory() {
        return fasDataSourceFactory;
    }

    public void setFasDataSourceFactory(DataSourceFactory fasDataSourceFactory) {
        this.fasDataSourceFactory = fasDataSourceFactory;
    }

    public DataSourceFactory getLisDataSourceFactory() {
        return lisDataSourceFactory;
    }

    public void setLisDataSourceFactory(DataSourceFactory lisDataSourceFactory) {
        this.lisDataSourceFactory = lisDataSourceFactory;
    }

    public DataSourceFactory getNsDataSourceFactory() {
        return nsDataSourceFactory;
    }

    public void setNsDataSourceFactory(DataSourceFactory nsDataSourceFactory) {
        this.nsDataSourceFactory = nsDataSourceFactory;
    }

    public DataSourceFactory getCmsDataSourceFactory() {
        return cmsDataSourceFactory;
    }

    public void setCmsDataSourceFactory(DataSourceFactory cmsDataSourceFactory) {
        this.cmsDataSourceFactory = cmsDataSourceFactory;
    }

    public DataSourceFactory getCmsrsDataSourceFactory() {
        return cmsrsDataSourceFactory;
    }

    public void setCmsrsDataSourceFactory(DataSourceFactory cmsrsDataSourceFactory) {
        this.cmsrsDataSourceFactory = cmsrsDataSourceFactory;
    }
}
