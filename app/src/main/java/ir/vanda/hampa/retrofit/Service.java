package ir.vanda.hampa.retrofit;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Service
{
    @GET("index")
    Call<Index> index();


    @GET("cart")
    Call<IndexCart> indexCart();

    @POST("auth/login")
    Call<Login> login(@Body HashMap<String, String> requestBody);

    @POST("auth/register")
    Call<Register> register(@Body HashMap<String, String> requestBody);


    //lessonExams
    @GET("lessonExams")
    Call<LessonExamList> lessonExams(@Query("page") int current_page, @QueryMap HashMap<String, String> data);

    @POST("lessonExams/add")
    Call<LessonExamAdd> lessonExamsAdd(@Body HashMap<String, String> data);
}
