package ir.vanda.hampa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.io.IOException;

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

    public static Retrofit retrofit;
    public static Storage  storage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        SharedPreferences sp = getPreferences(MODE_PRIVATE);

        storage = new Storage(sp);

        final Student student = (Student) storage.get("student");

        final String access_token = student != null ? student.access_token : "";


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor()
        {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request original = chain.request();

                Request request = original.newBuilder()
                                          .header("Authorization", "Bearer " + access_token)
                                          .method(original.method(), original.body())
                                          .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.4/hamta/public/api/")
                                         .addConverterFactory(GsonConverterFactory.create())
                                         .client(client)
                                         .build();

    }

    public  Storage getStorage()
    {
        return storage;
    }

    public  Service getService()
    {
        return retrofit.create(Service.class);
    }
}
