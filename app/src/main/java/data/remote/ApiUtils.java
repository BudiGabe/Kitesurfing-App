package data.remote;


public class ApiUtils {

    private ApiUtils(){}

    public static final String BASE_URL = "https://internship-2019.herokuapp.com/";

    public static APIService getAPIService(){
        //generate an implementation of the APIService interface
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
