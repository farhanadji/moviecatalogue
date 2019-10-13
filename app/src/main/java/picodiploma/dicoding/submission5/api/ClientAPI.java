package picodiploma.dicoding.submission5.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientAPI {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private movieAPI api;
    private static ClientAPI instance = null;

    private ClientAPI(){
        Retrofit retrofitmov = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofitmov.create(movieAPI.class);
    }

    public static ClientAPI getInstance(){
        if(instance == null){
            instance = new ClientAPI();
        }
        return instance;
    }

    public movieAPI getApi(){
        return api;
    }

}
