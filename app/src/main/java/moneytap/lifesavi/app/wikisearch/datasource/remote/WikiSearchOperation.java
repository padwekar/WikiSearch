package moneytap.lifesavi.app.wikisearch.datasource.remote;

import io.reactivex.Single;
import moneytap.lifesavi.app.wikisearch.app.constant.Constant;
import moneytap.lifesavi.app.wikisearch.model.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WikiSearchOperation {

    @GET(Constant.UrlConstant.SEARCH_URL)
    Single<Response> getSearchResults(@Query(Constant.Query.SEARCH_QUERY) String searchString, @Query(Constant.Query.OFFSET) int offset);

}
