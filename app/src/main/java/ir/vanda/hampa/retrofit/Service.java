package ir.vanda.hampa.retrofit;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import ir.vanda.hampa.model.LessonExam;
import ir.vanda.hampa.model.Question;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface Service
{
    //checkUpdate
    @GET("checkUpdate")
    Call<CheckUpdate> checkUpdate(@QueryMap HashMap<String, String> body);

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

    @GET("profile/myProfile")
    Call<MyProfile> myProfile();

    @POST("profile/updateMyProfile")
    Call<UpdateMyProfile> updateMyProfile(@Body HashMap<String, String> body);


    //discount
    @GET("profile/discountCodes")
    Call<DiscountCode> discountCodes();

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

    @POST("lessonExams/finishExam/{lessonExamId}")
    Call<FinishExam> finishExam(@Path("lessonExamId") int LessonExamId, @Body JsonObject body);

    @GET("lessonExams/result/{lessonExamId}")
    Call<LessonExamResult> result(@Path("lessonExamId") int LessonExamId);

    // scholarship
    @GET("scholarShip")
    Call<ScholarshipCall> scholarShip();

    @POST("submitScholarShip")
    Call<ScholarshipCall> submitScholarShip(@Body HashMap<String, String> body);


}
