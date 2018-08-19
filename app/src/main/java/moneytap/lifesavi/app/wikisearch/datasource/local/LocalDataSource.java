package moneytap.lifesavi.app.wikisearch.datasource.local;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.Contract;

import io.paperdb.Paper;
import moneytap.lifesavi.app.wikisearch.model.Response;

public class LocalDataSource {

    private static LocalDataSource localDataSource;

    private LocalDataSource(){}

    public static LocalDataSource getInstance() {
        if(localDataSource==null){
            localDataSource = new LocalDataSource();
        }
        return localDataSource;
    }

    public Response search(String topic,int offset){
        return Paper.book().read(topicOffsetKey(topic,offset),new Response());
    }

    public void save(Response response,String topic,int offset){
         Paper.book().write(topicOffsetKey(topic,offset),response);
    }

    @NonNull
    @Contract(pure = true)
    private String topicOffsetKey(String topic, int offset){
        return topic+"*"+offset;
    }
}
