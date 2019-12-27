package ir.vanda.hampa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;

import ir.vanda.hampa.activity.AuthActivity;
import ir.vanda.hampa.activity.SplashActivity;
import ir.vanda.hampa.component.HampaDialog;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.lib.Font;
import ir.vanda.hampa.lib.Storage;
import ir.vanda.hampa.model.Student;
import ir.vanda.hampa.retrofit.Service;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BaseActivity extends AppCompatActivity
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

        retrofit = new Retrofit.Builder().baseUrl("http://www.hampaa.net/api/")
                                         .addConverterFactory(GsonConverterFactory.create())
                                         .client(client)
                                         .build();

    }


    public Storage getStorage()
    {
        return storage;
    }

    public Service getService()
    {
        return retrofit.create(Service.class);
    }


    public void makeToast(String message)
    {
        Toast        toast       = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        toastLayout.setBackgroundResource(R.drawable.bg_toast);

        TextView toastMessage = (TextView) toastLayout.getChildAt(0);
        toastMessage.setTextSize(18);
        toastMessage.setTypeface(Font.iranSans_light(getApplicationContext()));
        toastMessage.setTextColor(Color.WHITE);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public HampaDialog onFailureDialog(Context context)
    {
        HampaDialog dialog = new HampaDialog(context);
        dialog.show();

        dialog.setValues(R.drawable.bg_no_internet,"خطا در اتصال","مشکلی در برقراری ارتباط وجود داره. ممکنه مشکل از اینترنت شما باشه.","تلاش مجدد",null);

        return dialog;
    }


}
