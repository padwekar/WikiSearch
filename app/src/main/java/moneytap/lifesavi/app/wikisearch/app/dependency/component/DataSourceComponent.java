package moneytap.lifesavi.app.wikisearch.app.dependency.component;

import javax.inject.Singleton;

import dagger.Component;
import moneytap.lifesavi.app.wikisearch.datasource.remote.RemoteDataSource;
import moneytap.lifesavi.app.wikisearch.app.dependency.module.NetworkModule;

@Singleton
@Component(modules = {NetworkModule.class})
public interface DataSourceComponent {
    void inject(RemoteDataSource dataSource);
}
