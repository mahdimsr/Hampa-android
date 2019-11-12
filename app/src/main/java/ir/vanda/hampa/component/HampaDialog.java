package ir.vanda.hampa.component;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.vanda.hampa.R;

public class HampaDialog extends Dialog
{
    private ImageView     bg;
    private VandaTextView title, message, defaultBtn, cancelBtn;
    private OnDefaultClickListener onDefaultClickListener;
    private OnCancelClickListener  onCancelClickListener;
    private HampaDialog            hampaDialog;

    public HampaDialog(@NonNull Context context)
    {
        super(context);

        hampaDialog = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hampa_dialog);

        bg = findViewById(R.id.bg);

        title      = findViewById(R.id.title);
        message    = findViewById(R.id.message);
        defaultBtn = findViewById(R.id.defaultButton);
        cancelBtn  = findViewById(R.id.cancelButton);

        defaultBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onDefaultClickListener != null)
                {
                    onDefaultClickListener.onClick(hampaDialog);
                }
                else
                {
                    hampaDialog.dismiss();
                }
            }
        });


        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onCancelClickListener != null)
                {
                    onCancelClickListener.onClick(hampaDialog);
                }
                else
                {
                    hampaDialog.dismiss();
                }
            }
        });

    }

    public void setValues(Integer bgResource, @Nullable String title, @Nullable String message, @Nullable String defaultText, String cancelText)
    {
        if (bgResource != null)
        {
            bg.setImageResource(bgResource);
        }

        this.title.setText(title);
        this.message.setText(message);

        defaultBtn.setText(defaultText);

        if (cancelText != null)
        {
            cancelBtn.setVisibility(View.VISIBLE);
            cancelBtn.setText(cancelText);
        }
    }

    public void setOnDefaultClickLisetener(OnDefaultClickListener onDefaultClickListener)
    {
        this.onDefaultClickListener = onDefaultClickListener;
    }

    public void setOnCancelClickLisetener(OnCancelClickListener onCancelClickListener)
    {
        this.onCancelClickListener = onCancelClickListener;
    }


    //interface
    public interface OnDefaultClickListener
    {
        void onClick(Dialog dialog);
    }

    public interface OnCancelClickListener
    {
        void onClick(Dialog dialog);
    }
}
