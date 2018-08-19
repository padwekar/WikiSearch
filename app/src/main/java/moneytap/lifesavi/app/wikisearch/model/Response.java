package moneytap.lifesavi.app.wikisearch.model;


import com.google.gson.annotations.SerializedName;

public class Response {

    public String topicOffSetKey = "";

    @SerializedName("continue")
    public Paginate paginate = new Paginate();

    public Query query = new Query();


}