package ir.vanda.hampa.retrofit;

import java.util.HashMap;
import java.util.List;

import ir.vanda.hampa.model.LessonExam;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Service
{
    //auth

    @POST("auth/login")
    Call<Login> login(@Body HashMap<String, String> requestBody);

    @POST("auth/register")
    Call<Register> register(@Body HashMap<String, String> requestBody);


    @GET("index")
    Call<Index> index();

    //profile
    @GET("profile/myExams")
    Call<MyExams> myExams();


    //cart

    @GET("cart")
    Call<IndexCart> indexCart();

    @POST("cart/remove")
    Call<RemoveCart> removeCart(@Body HashMap<String, String> data);

    @POST("cart/purchase")
    Call<Purchase> purchase(@Body HashMap<String, List<Integer>> data);


    @GET("cart/transactions")
    Call<TransactionsList> transactions();


    //lessonExams
    @GET("lessonExams")
    Call<LessonExamList> lessonExams(@Query("page") int current_page, @QueryMap HashMap<String, String> data);

    @POST("lessonExams/add")
    Call<LessonExamAdd> lessonExamsAdd(@Body HashMap<String, String> data);
}
