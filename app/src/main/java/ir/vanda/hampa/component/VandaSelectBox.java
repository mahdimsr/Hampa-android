package ir.vanda.hampa.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import ir.vanda.hampa.R;

/**
 * TODO: document your custom view class.
 */
public class VandaSelectBox extends RelativeLayout
{
    private Spinner        spinner;
    private String         hint;
    private VandaTextView  error;
    private RelativeLayout inputLayer;

    public VandaSelectBox(Context context)
    {
        super(context);
        init(null, 0);
    }

    public VandaSelectBox(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public VandaSelectBox(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void inflate()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.component_vanda_select_box, this);

        spinner    = findViewById(R.id.spinner);
        error      = findViewById(R.id.error);
        inputLayer = findViewById(R.id.inputLayer);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        //inflate
        inflate();

        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.VandaSelectBox, defStyle, 0);

        hint = typedArray.getString(R.styleable.VandaSelectBox_hintBox);

        typedArray.recycle();

    }


    public void setHint(String hint)
    {
        this.hint = hint;
    }

    public void addData(List<String> data)
    {
        if (hint != null)
        {
            data.add(0, hint);
        }

        CustomAdapter adapter = new CustomAdapter(getContext(), data);

        spinner.setAdapter(adapter);
    }

    public Spinner getSpinner()
    {
        return spinner;
    }


    //spinner adapter
    private class CustomAdapter extends ArrayAdapter<String>
    {
        public CustomAdapter(@NonNull Context context, @NonNull List<String> objects)
        {
            super(context, 0, objects);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {

            return itemViewSelect(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
        {
            return itemViewList(position, convertView, parent);
        }

        private View itemViewList(int position, View convertView, ViewGroup parent)
        {
            String item = getItem(position);


            View v = convertView;

            if (v == null)
            {
                v = LayoutInflater.from(getContext()).inflate(R.layout.adapter_select_box, parent, false);
            }

            if (hint != null && position == 0)
            {
                v.setEnabled(false);
                v.setOnClickListener(null);

            }

            VandaTextView title = v.findViewById(R.id.title);
            title.setText(item);

            return v;
        }

        private View itemViewSelect(int position, View convertView, ViewGroup parent)
        {
            String item = getItem(position);

            View v = convertView;

            if (v == null)
            {
                v = LayoutInflater.from(getContext()).inflate(R.layout.adapter_select_box_selected, parent, false);
            }


            VandaTextView title = v.findViewById(R.id.title);
            title.setText(item);

            return v;
        }
    }

    public void setError(String errorText)
    {
        if (errorText != null)
        {
            error.setText(errorText);

            setErrorStyle();
        }
        else
        {
            setNormal();
        }
    }

    public void setNormal()
    {
        inputLayer.setBackground(getResources().getDrawable(R.drawable.bg_vanda_input));

        error.setVisibility(GONE);
    }


    private void setErrorStyle()
    {
        YoYo.with(Techniques.Shake).duration(200).playOn(this);

        error.setTextColor(getResources().getColor(R.color.colorHasError));
        error.setVisibility(VISIBLE);

        inputLayer.setBackground(getResources().getDrawable(R.drawable.bg_vanda_input_error));
    }


}
