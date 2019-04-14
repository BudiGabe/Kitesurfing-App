package data.remote;


import data.model.TokenPost;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//used to execute HTTP requests

public interface APIService {
    @Headers("Accept:application/json")
    @POST("/api-user-get")
    @FormUrlEncoded
    Call<TokenPost> getToken(@Field("email") String email);
}
