package ir.vanda.hampa.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service
{
    @GET("index")
    Call<Index> index();
}
