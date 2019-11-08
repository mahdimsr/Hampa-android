package ir.vanda.hampa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ir.vanda.hampa.R;

public class ErrorActivity extends AppCompatActivity
{
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        //initialize
        i = getIntent();


        Toast.makeText(this, "" + i.getIntExtra("code",0), Toast.LENGTH_SHORT).show();


    }
}
