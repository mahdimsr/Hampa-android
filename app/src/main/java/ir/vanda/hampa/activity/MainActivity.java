package ir.vanda.hampa.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import ir.vanda.hampa.BasicActivity;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.BottomMenu;

public class MainActivity extends BasicActivity
{
    private BottomMenu bottomMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        bottomMenu.setOnBottomMenuItemClick(new BottomMenu.OnBottomMenuItemClick()
        {
            @Override
            public void onItemClick(int position)
            {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        bottomMenu.setDefaultSelected(1);

    }

    private void findViews()
    {
        bottomMenu = findViewById(R.id.bottomMenu);
    }
}
