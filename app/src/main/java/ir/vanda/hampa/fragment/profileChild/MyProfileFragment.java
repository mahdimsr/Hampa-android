package ir.vanda.hampa.fragment.profileChild;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.activity.ErrorActivity;
import ir.vanda.hampa.component.HampaLoader;
import ir.vanda.hampa.component.VandaInput;
import ir.vanda.hampa.component.VandaSelectBox;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.lib.Error;
import ir.vanda.hampa.model.Grade;
import ir.vanda.hampa.model.Orientation;
import ir.vanda.hampa.model.Student;
import ir.vanda.hampa.retrofit.MyProfile;
import ir.vanda.hampa.retrofit.UpdateMyProfile;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfileFragment extends BaseFragment
{


    public MyProfileFragment()
    {
        // Required empty public constructor
    }

    private VandaSelectBox orientationSelect, gradeSelect;
    private VandaInput nameInput, familyInput, emailInput, nationalCodeInput;
    private VandaTextView submit;
    private HampaLoader   hampaLoader;

    private List<Grade>       grades;
    private List<Orientation> orientations;

    private List<String> gradeStringList, orientationStringList;
    private HashMap<String, String> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_profile, container, false);
        findViews(v);

        data = new HashMap<>();

        hampaLoader.startAnimate();

        gradeStringList       = new ArrayList<>();
        orientationStringList = new ArrayList<>();

        Call<MyProfile> myProfileCall = getService().myProfile();
        myProfileCall.enqueue(new Callback<MyProfile>()
        {
            @Override
            public void onResponse(Call<MyProfile> call, Response<MyProfile> response)
            {
                Response<MyProfile> res  = response;
                final MyProfile     body = res.body();

                hampaLoader.setVisibility(View.GONE);

                if (res.isSuccessful())
                {
                    if (body.status.equals("OK"))
                    {

                        Student student      = body.student;
                        String  access_token = ((Student) getStorage().get("student")).access_token;

                        student.access_token = access_token;
                        getStorage().put(student, "student");


                        grades       = body.grades;
                        orientations = body.orientations;


                        for (Grade grade : grades)
                        {
                            gradeStringList.add(grade.title);
                        }

                        gradeSelect.setHint("پایه تحصیلی");
                        gradeSelect.addData(gradeStringList);

                        gradeSelect.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                        {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                            {
                                Grade grade = null;

                                for (Grade g : grades)
                                {
                                    if (g.title.equals(gradeStringList.get(position)))
                                    {
                                        grade = g;
                                    }
                                }

                                if (grade != null)
                                {
                                    data.put("gradeId", grade.id + "");
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent)
                            {

                            }
                        });

                        for (Orientation orientation : orientations)
                        {
                            orientationStringList.add(orientation.title);
                        }

                        orientationSelect.setHint("رشته تحصیلی");
                        orientationSelect.addData(orientationStringList);

                        orientationSelect.getSpinner().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                        {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                            {
                                Orientation orientation = null;

                                for (Orientation o : orientations)
                                {
                                    if (o.title.equals(orientationStringList.get(position)))
                                    {
                                        orientation = o;
                                    }
                                }

                                if (orientation != null)
                                {
                                    data.put("orientationId", orientation.id + "");
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent)
                            {

                            }
                        });

                        if (student.isComplete)
                        {
                            nameInput.getInput().setText(student.name);
                            familyInput.getInput().setText(student.familyName);
                            emailInput.getInput().setText(student.email);
                            nationalCodeInput.setVisibility(View.GONE);

                            gradeSelect.getSpinner().setSelection(student.gradeId);

                            orientationSelect.getSpinner().setSelection(student.orientationId);
                        }

                    }
                    else
                    {
                        Toast.makeText(getContext(), "مشکلی در سامانه وجود دارد.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Intent i = new Intent(getContext(), ErrorActivity.class);

                    i.putExtra("title", "خطای پروفایل من");
                    i.putExtra("type", res.message());
                    i.putExtra("code", res.code());

                    startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t)
            {
                hampaLoader.setVisibility(View.GONE);

                Log.i("lessonExamResError", t.toString());

                Intent i = new Intent(getContext(), ErrorActivity.class);

                i.putExtra("title", "خطای خطای پروفایل من");
                i.putExtra("type", t.toString());
                i.putExtra("code", t.getMessage());

                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener()
        {
            Student student = (Student) getStorage().get("student");

            @Override
            public void onClick(View v)
            {
                String       name         = nameInput.getInput().getText().toString().trim();
                String       family       = familyInput.getInput().getText().toString().trim();
                final String email        = emailInput.getInput().getText().toString().trim();
                String       nationalCode = nationalCodeInput.getInput().getText().toString().trim();


                data.put("name", name);
                data.put("family", family);
                data.put("email", email);

                if (!student.isComplete)
                {
                    data.put("nationalCode", nationalCode);
                }

                hampaLoader.setVisibility(View.VISIBLE);

                Call<UpdateMyProfile> updateMyProfileCall = getService().updateMyProfile(data);
                updateMyProfileCall.enqueue(new Callback<UpdateMyProfile>()
                {
                    @Override
                    public void onResponse(Call<UpdateMyProfile> call, Response<UpdateMyProfile> response)
                    {
                        Response<UpdateMyProfile> res  = response;
                        UpdateMyProfile           body = res.body();

                        hampaLoader.setVisibility(View.GONE);

                        if (res.isSuccessful())
                        {
                            if (body.status.equals("Validation"))
                            {
                                Error error = new Error(body.errors);

                                nameInput.setError(error.get("name"));
                                familyInput.setError(error.get("family"));
                                emailInput.setError(error.get("email"));
                                nationalCodeInput.setError(error.get("nationalCode"));
                                gradeSelect.setError(error.get("gradeId"));
                                orientationSelect.setError(error.get("orientationId"));
                            }
                            else if (body.status.equals("OK"))
                            {
                                String access_token = ((Student) getStorage().get("student")).access_token;

                                Student student = body.student;
                                student.access_token = access_token;

                                getStorage().put(student, "student");

                                makeToast("تغییرات با موفقیت اعمال شد");

                                getActivity().onBackPressed();
                            }
                        }
                        else
                        {
                            makeToast(getString(R.string.serverError));
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateMyProfile> call, Throwable t)
                    {
                        makeToast(getString(R.string.serverError));
                        hampaLoader.setVisibility(View.GONE);
                    }
                });

            }
        });

        return v;
    }


    private void findViews(View v)
    {
        gradeSelect       = v.findViewById(R.id.gradeSelect);
        orientationSelect = v.findViewById(R.id.orientationSelect);

        hampaLoader = v.findViewById(R.id.hampaLoader);
        submit      = v.findViewById(R.id.submit);


        nameInput         = v.findViewById(R.id.name);
        familyInput       = v.findViewById(R.id.family);
        emailInput        = v.findViewById(R.id.email);
        nationalCodeInput = v.findViewById(R.id.nationalCode);

    }
}
