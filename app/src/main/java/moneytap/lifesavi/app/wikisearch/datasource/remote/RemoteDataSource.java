package moneytap.lifesavi.app.wikisearch.datasource.remote;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import moneytap.lifesavi.app.wikisearch.app.dependency.component.DaggerDataSourceComponent;
import moneytap.lifesavi.app.wikisearch.model.Response;

public class RemoteDataSource {

    private static RemoteDataSource dataSource;

    @Inject
    public WikiSearchOperation searchOperation;

    private RemoteDataSource(){
        DaggerDataSourceComponent.builder().build().inject(this);
    }

    public static RemoteDataSource getInstance() {
        dataSource = new RemoteDataSource();

        return dataSource;
    }


    public Single<Response> search(String topic, final int offset){
        return searchOperation.getSearchResults(topic,offset).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
