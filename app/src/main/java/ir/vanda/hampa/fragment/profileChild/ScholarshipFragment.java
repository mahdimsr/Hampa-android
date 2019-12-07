package ir.vanda.hampa.fragment.profileChild;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Objects;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.lib.Font;
import ir.vanda.hampa.model.Scholarship;
import ir.vanda.hampa.retrofit.ScholarshipCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScholarshipFragment extends BaseFragment
{


    public ScholarshipFragment()
    {
        // Required empty public constructor
    }

    private LinearLayout requestLayout, replyLayout;
    private EditText      requestEditText;
    private VandaTextView submitButton, stdMessageTextView, adminMessageTextView, resultState;
    private ImageView closeImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_scholarship, container, false);
        findViews(v);


        Call<ScholarshipCall> scholarshipCall = getService().scholarShip();
        scholarshipCall.enqueue(new Callback<ScholarshipCall>()
        {
            @Override
            public void onResponse(Call<ScholarshipCall> call, Response<ScholarshipCall> response)
            {
                Response<ScholarshipCall> res = response;

                ScholarshipCall body = res.body();

                if (res.isSuccessful())
                {
                    checkScholarshipState(body.scholarship);
                }
            }

            @Override
            public void onFailure(Call<ScholarshipCall> call, Throwable t)
            {
                makeToast("خطا در اتصال");
            }
        });

        closeImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getActivity().onBackPressed();
            }
        });

        return v;
    }

    private void findViews(View v)
    {
        requestLayout = v.findViewById(R.id.requestLayout);
        replyLayout   = v.findViewById(R.id.replyLayout);

        closeImage = v.findViewById(R.id.closeImage);

        requestEditText = v.findViewById(R.id.requestEditText);
        requestEditText.setTypeface(Font.iranSans_light(Objects.requireNonNull(getContext())));

        stdMessageTextView   = v.findViewById(R.id.stdMessageTextView);
        adminMessageTextView = v.findViewById(R.id.adminMessageTextView);
        resultState          = v.findViewById(R.id.resultState);
        submitButton         = v.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String text = requestEditText.getText().toString();

                if (text.isEmpty())
                {
                    makeToast("متن درخواست نمیتواند خالی باشد.");
                }
                else
                {
                    HashMap<String, String> body = new HashMap<>();

                    body.put("stdMessage", text);

                    Call<ScholarshipCall> scholarshipCall = getService().submitScholarShip(body);
                    scholarshipCall.enqueue(new Callback<ScholarshipCall>()
                    {
                        @Override
                        public void onResponse(Call<ScholarshipCall> call, Response<ScholarshipCall> response)
                        {
                            Response<ScholarshipCall> res = response;

                            ScholarshipCall body = res.body();

                            Log.i("scholarship", res.code() + "");

                            if (res.isSuccessful())
                            {
                                checkScholarshipState(body.scholarship);
                            }
                        }

                        @Override
                        public void onFailure(Call<ScholarshipCall> call, Throwable t)
                        {
                            makeToast("خطا در اتصال");
                        }
                    });
                }

            }
        });
    }


    private void checkScholarshipState(Scholarship scholarship)
    {
        if (scholarship == null)
        {
            requestLayout.setVisibility(View.VISIBLE);
            replyLayout.setVisibility(View.GONE);
        }
        else
        {
            requestLayout.setVisibility(View.GONE);
            replyLayout.setVisibility(View.VISIBLE);

            stdMessageTextView.setText(scholarship.stdMessage);

            if (scholarship.adminMessage == null)
            {
                adminMessageTextView.setText("هنوز مدیر پیامی درج نکرده");

            }
            else
            {
                adminMessageTextView.setText(scholarship.adminMessage);
            }

            resultState.setText("وضعیت رسیدگی به درخواست: " + scholarship.persian_status);
        }
    }
}
