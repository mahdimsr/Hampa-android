package ir.vanda.hampa.retrofit;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service
{
    @GET("index")
    Call<Index> index();

    @POST("auth/login")
    Call<Login> login(@Body HashMap<String, String> requestBody);

    @POST("auth/register")
    Call<Register> register(@Body HashMap<String, String> requestBody);

    //lessonExams
    @GET("lessonExams")
    Call<LessonExamList> lessonExams(@Query("page") int current_page);
}
