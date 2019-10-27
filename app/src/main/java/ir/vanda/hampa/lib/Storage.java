package ir.vanda.hampa.lib;

import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Storage
{
    private SharedPreferences sp;

    public Storage(SharedPreferences sp)
    {
        this.sp = sp;
    }

    public void put(Object o, String name)
    {
        Gson gson = new Gson();

        String value = gson.toJson(o);

        sp.edit().putString(name, value).apply();
        sp.edit().putString(name + "type", o.getClass().getName()).apply();
    }

    public Object get(String name)
    {
        String value = sp.getString(name, null);
        String type  = sp.getString(name + "type", null);

        if (value == null || type == null)
        {
            return null;
        }
        else
        {
            try
            {
                Gson   gson = new Gson();
                Object o    = gson.fromJson(value, Class.forName(type));

                return o;
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean has(String name)
    {
        return get(name) != null;
    }


}
