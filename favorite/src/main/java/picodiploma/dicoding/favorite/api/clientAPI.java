package picodiploma.dicoding.favorite.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class clientAPI {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private movieAPI api;
    private static clientAPI instance = null;

    private clientAPI(){
        Retrofit retrofitmov = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofitmov.create(movieAPI.class);
    }

    public static clientAPI getInstance(){
        if(instance == null){
            instance = new clientAPI();
        }
        return instance;
    }

    public movieAPI getApi(){
        return api;
    }

}
