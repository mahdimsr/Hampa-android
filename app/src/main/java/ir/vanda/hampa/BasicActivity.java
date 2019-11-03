package ir.vanda.hampa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import java.io.IOException;

import ir.vanda.hampa.activity.AuthActivity;
import ir.vanda.hampa.activity.SplashActivity;
import ir.vanda.hampa.lib.Storage;
import ir.vanda.hampa.model.Student;
import ir.vanda.hampa.retrofit.Service;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasicActivity extends AppCompatActivity
{

    private static Retrofit retrofit;
    private static Storage  storage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("storage", MODE_PRIVATE);

        storage = new Storage(sp);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor()
        {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request original = chain.request();


                if (getStorage().has("student"))
                {

                    Student student = (Student) getStorage().get("student");

                    Request request = original.newBuilder()
                                              .header("Authorization", "Bearer " + student.access_token)
                                              .method(original.method(), original.body())
                                              .build();

                    return chain.proceed(request);
                }
                else
                {
                    Request request = original.newBuilder()
                                              .method(original.method(), original.body())
                                              .build();

                    return chain.proceed(request);
                }


            }
        });

        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.2/hamta/public/api/")
                                         .addConverterFactory(GsonConverterFactory.create())
                                         .client(client)
                                         .build();

    }

    public float pxToDp(int px)
    {
        return px / ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public int dpToPx(float dp)
    {
        return (int) (dp * ((float) this.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public Storage getStorage()
    {
        return storage;
    }

    public Service getService()
    {
        return retrofit.create(Service.class);
    }
}
