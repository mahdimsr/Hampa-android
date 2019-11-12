package ir.vanda.hampa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ir.vanda.hampa.R;
import ir.vanda.hampa.component.VandaSelectBox;

public class TestActivity extends AppCompatActivity
{

    private VandaSelectBox testSelectBox;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testSelectBox = findViewById(R.id.testSelectBox);

    }
}
