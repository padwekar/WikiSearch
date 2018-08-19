package moneytap.lifesavi.app.wikisearch.app;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import io.paperdb.Paper;

public class WikiSearchApplication extends Application {

    public static WikiSearchApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Paper.init(this);
    }

    public Context getContext(){
        return this.getApplicationContext();
    }
}
