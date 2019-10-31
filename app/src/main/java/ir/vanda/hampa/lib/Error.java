package ir.vanda.hampa.lib;

import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.JsonObject;

import ir.vanda.hampa.component.VandaTextView;


public class Error
{
    private JsonObject errors;

    public Error(JsonObject errors)
    {
        this.errors = errors;
    }

    public boolean has(String key)
    {
        return errors != null && errors.has(key);
    }

    public String get(String key)
    {
        return has(key) ? errors.get(key).getAsString() : null;
    }


    public static void setError(VandaTextView textView,String error)
    {
        textView.setText(error);
        textView.setVisibility(View.VISIBLE);

        YoYo.with(Techniques.Shake).duration(200).playOn(textView);

    }
}
