package ir.vanda.hampa.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import ir.vanda.hampa.BaseActivity;
import ir.vanda.hampa.R;

public class ErrorActivity extends BaseActivity
{
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        //initialize
        i = getIntent();


    }


    @Override
    public void onBackPressed()
    {

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

    }

}
