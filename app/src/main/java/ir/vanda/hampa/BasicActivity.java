package ir.vanda.hampa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ir.vanda.hampa.retrofit.Service;
import retrofit2.Retrofit;

public class BasicActivity extends AppCompatActivity {

    public Retrofit retrofit;
    public Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").build();

        service = retrofit.create(Service.class);

    }
}
