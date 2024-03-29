package data.remote;


import java.util.Map;

import data.model.Params;
import data.model.posts.AddFavPOST;
import data.model.posts.GetAllSpotsPOST;
import data.model.posts.GetSpotCountriesPOST;
import data.model.posts.GetSpotDetPOST;
import data.model.posts.RemoveFavPOST;
import data.model.posts.TokenPost;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//used to execute HTTP requests

public interface APIService {
    @Headers({
            "Content-Type:application/json"
    })
    @POST("/api-user-get")
    Call<TokenPost> getToken(@Body Map<String, String> email);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/api-spot-get-all")
    Call<GetAllSpotsPOST> getAllSpots(@Header("token") String token,
                                      @Body Map<String,String> data);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/api-spot-get-all")
    Call<GetAllSpotsPOST> getAllSpotsFiltered(@Header("token") String token,
                                              @Body Params params);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/api-spot-get-details")
    Call<GetSpotDetPOST> getSpotDet(@Header("token") String token,
                                    @Body Map<String,String> data);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/api-spot-get-countries")
    Call<GetSpotCountriesPOST> getSpotCountries(@Header("token") String token);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/api-spot-favorites-add")
    Call<AddFavPOST> addSpotFav (@Header("token") String token,
                                 @Body Map<String,String> data);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("/api-spot-favorites-remove")
    Call<RemoveFavPOST>remSpotFav (@Header("token") String token,
                                   @Body Map<String,String> data);
}
