package moneytap.lifesavi.app.wikisearch.app.dependency.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import moneytap.lifesavi.app.wikisearch.app.WikiSearchApplication;
import moneytap.lifesavi.app.wikisearch.app.constant.Constant;
import moneytap.lifesavi.app.wikisearch.datasource.remote.WikiSearchOperation;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    public Retrofit retrofit(OkHttpClient httpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constant.UrlConstant.BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(5, TimeUnit.SECONDS);
        builder.connectTimeout(3, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        builder.addInterceptor(interceptor);

        return builder.build();
    }

    @Provides
    public WikiSearchOperation operation(Retrofit retrofit) {
        return retrofit.create(WikiSearchOperation.class);
    }

}
