package moneytap.lifesavi.app.wikisearch.controller;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import moneytap.lifesavi.app.wikisearch.app.utils.Utils;
import moneytap.lifesavi.app.wikisearch.datasource.local.LocalDataSource;
import moneytap.lifesavi.app.wikisearch.datasource.remote.RemoteDataSource;
import moneytap.lifesavi.app.wikisearch.model.Response;

public class DataSearchController {


    public static DataSearchController getInstance(){
        return new DataSearchController();
    }


    public interface OnDataChangeListener {
        void onSuccess(Response response);
        void onError(Throwable e);
    }


    public void search(final String topic, final int offset,final String currentKey, final OnDataChangeListener listener){
        if(Utils.isNetworkAvailable()){
            RemoteDataSource.getInstance().search(topic,offset).subscribe(new SingleObserver<Response>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onSuccess(Response response) {
                    if(listener!=null){
                        response.topicOffSetKey = currentKey;
                        listener.onSuccess(response);
                    }
                    LocalDataSource.getInstance().save(response,topic,offset); //Save to Local Cache
                }

                @Override
                public void onError(Throwable e) {
                    if(listener!=null){
                        listener.onError(e);
                    }
                }
            });
        } else { //Return from cache
            listener.onSuccess(LocalDataSource.getInstance().search(topic,offset));
            listener.onError(new Throwable("Your device is offline.")); // Inform about no internet
        }
    }
}
