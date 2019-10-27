package ir.vanda.hampa.retrofit;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Service
{
    @GET("index")
    Call<Index> index();

    @POST("auth/login")
    Call<Login> login(@Body HashMap<String,String> requestBody);

}
