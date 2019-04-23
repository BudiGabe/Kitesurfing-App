package data.remote;


import data.model.POSTS.AddFavPOST;
import data.model.POSTS.GetAllSpotsPOST;
import data.model.POSTS.GetSpotCountriesPOST;
import data.model.POSTS.GetSpotDetPOST;
import data.model.POSTS.RemoveFavPOST;
import data.model.POSTS.TokenPost;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//used to execute HTTP requests

public interface APIService {
    @Headers("Accept:application/json")
    @POST("/api-user-get")
    @FormUrlEncoded
    Call<TokenPost> getToken(@Field("email") String email);

    @Headers("Accept:application/json")
    @POST("/api-spot-get-all")
    @FormUrlEncoded
    Call<GetAllSpotsPOST> getAllSpots(@Header("token") String token,
                                      @Field("country") String country,
                                      @Field("windProbability") String windProbability);

    @Headers("Accept:application/json")
    @POST("/api-spot-get-all")
    @FormUrlEncoded
    Call<GetAllSpotsPOST> getAllSpots(@Header("token") String token,
                                      @Field("country") String country,
                                      @Field("windProbability") float windProbability);

    @Headers("Accept:application/json")
    @POST("/api-spot-get-details")
    @FormUrlEncoded
    Call<GetSpotDetPOST> getSpotDet(@Header("token") String token,
                                    @Field("spotId") String spotId);

    @Headers("Accept:application/json")
    @POST("/api-spot-get-countries")
    @FormUrlEncoded
    Call<GetSpotCountriesPOST> getSpotCountries(@Header("token") String token);

    @Headers("Accept:application/json")
    @POST("/api-spot-favorites-add")
    @FormUrlEncoded
    Call<AddFavPOST> addSpotFav (@Header("token") String token,
                                   @Field("spotId") String spotId);

    @Headers("Accept:application/json")
    @POST("/api-spot-favorites-remove")
    @FormUrlEncoded
    Call<RemoveFavPOST>remSpotFav (@Header("token") String token,
                                   @Field("spotId") String spotId);
}
