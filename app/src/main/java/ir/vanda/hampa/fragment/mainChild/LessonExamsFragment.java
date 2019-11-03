package ir.vanda.hampa.fragment.mainChild;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.HampaCheckBox;
import ir.vanda.hampa.component.LessonExamRecycler;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.model.LessonExam;
import ir.vanda.hampa.retrofit.LessonExamList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LessonExamsFragment extends BaseFragment
{


    public LessonExamsFragment()
    {
        // Required empty public constructor
    }

    private LessonExamRecycler recycler;
    private LinearLayout       filterLayout;
    private VandaTextView      title;

    private HampaCheckBox twelfthCheck, eveleventhCheck, tenthCheck;
    private HampaCheckBox mathematicCheck, scienceCheck, literalCheck;

    private List<HampaCheckBox> gradeCheckBoxList;
    private List<HampaCheckBox> orientationCheckBoxList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lesson_exams, container, false);
        findViews(v);

        final HashMap<String, String> data = new HashMap<>();

        recycler.setOnFetch(new LessonExamRecycler.OnFetch()
        {
            @Override
            public void onFetch(final LessonExamRecycler.OnLoadMore loadMore, int page)
            {


                Call<LessonExamList> examListCall = getService().lessonExams(page, data);

                examListCall.enqueue(new Callback<LessonExamList>()
                {
                    @Override
                    public void onResponse(Call<LessonExamList> call, Response<LessonExamList> response)
                    {
                        Response<LessonExamList> res  = response;
                        LessonExamList           body = res.body();

                        Log.i("lessonExamRes", call.request().url() + "");

                        if (body.status.equals("OK"))
                        {

                            List<LessonExam> examList = body.dataList.data;

                            if (examList != null || !examList.isEmpty())
                            {
                                loadMore.loadMore(body.dataList.data);
                            }
                            else
                            {
                                loadMore.complete();
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "loading is complete", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LessonExamList> call, Throwable t)
                    {
                        Log.i("lessonExamResError", t.toString());
                    }
                });
            }
        });

        recycler.load();


        recycler.setOnItemClickListener(new LessonExamRecycler.OnItemClickListener()
        {
            @Override
            public void OnClick(LessonExam lessonExam)
            {
                Toast.makeText(getContext(), "" + lessonExam.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
        {
            @Override
            public boolean onPreDraw()
            {


                return true;
            }
        });

    }

    private void findViews(View v)
    {
        recycler = v.findViewById(R.id.recyclerView);

        filterLayout = v.findViewById(R.id.filterLayout);
        title        = v.findViewById(R.id.title);

        gradeCheckBoxList       = new ArrayList<>();
        orientationCheckBoxList = new ArrayList<>();

        twelfthCheck = v.findViewById(R.id.twelfthCheckBox);
        gradeCheckBoxList.add(twelfthCheck);
        twelfthCheck.setOnCheckListener(new HampaCheckBox.OnCheckListener()
        {
            @Override
            public void OnCheck()
            {
                setGradeCheckBoxList(twelfthCheck);
            }
        });

        eveleventhCheck = v.findViewById(R.id.elevenCheckBox);
        gradeCheckBoxList.add(eveleventhCheck);
        eveleventhCheck.setOnCheckListener(new HampaCheckBox.OnCheckListener()
        {
            @Override
            public void OnCheck()
            {
                setGradeCheckBoxList(eveleventhCheck);
            }
        });

        tenthCheck = v.findViewById(R.id.tenCheckBox);
        gradeCheckBoxList.add(tenthCheck);
        tenthCheck.setOnCheckListener(new HampaCheckBox.OnCheckListener()
        {
            @Override
            public void OnCheck()
            {
                setGradeCheckBoxList(tenthCheck);
            }
        });


        mathematicCheck = v.findViewById(R.id.mathCheckBox);
        orientationCheckBoxList.add(mathematicCheck);
        mathematicCheck.setOnCheckListener(new HampaCheckBox.OnCheckListener()
        {
            @Override
            public void OnCheck()
            {
                setOrientationCheckBoxList(mathematicCheck);
            }
        });

        scienceCheck = v.findViewById(R.id.scienceCheckBox);
        orientationCheckBoxList.add(scienceCheck);
        scienceCheck.setOnCheckListener(new HampaCheckBox.OnCheckListener()
        {
            @Override
            public void OnCheck()
            {
                setOrientationCheckBoxList(scienceCheck);
            }
        });

        literalCheck = v.findViewById(R.id.literatureCheckBox);
        orientationCheckBoxList.add(literalCheck);
        literalCheck.setOnCheckListener(new HampaCheckBox.OnCheckListener()
        {
            @Override
            public void OnCheck()
            {
                setOrientationCheckBoxList(literalCheck);
            }
        });


    }

    private HampaCheckBox getGradeChecked()
    {
        HampaCheckBox selectedCheckBox = null;

        for (HampaCheckBox checkBox : gradeCheckBoxList)
        {
            if (checkBox.isChecked())
            {
                selectedCheckBox = checkBox;
            }
        }

        return selectedCheckBox;
    }

    private HampaCheckBox getOrientationChecked()
    {
        HampaCheckBox selectedCheckBox = null;

        for (HampaCheckBox checkBox : orientationCheckBoxList)
        {
            if (checkBox.isChecked())
            {
                selectedCheckBox = checkBox;
            }
        }

        return selectedCheckBox;
    }

    private void setGradeCheckBoxList(HampaCheckBox gradeCheckBox)
    {
        for (HampaCheckBox checkBox : gradeCheckBoxList)
        {
            if (checkBox == gradeCheckBox)
            {
                if (!checkBox.isChecked())
                {
                    checkBox.setCheck(false);
                }
                else
                {
                    checkBox.setCheck(true);
                }
            }
            else
            {
                checkBox.setCheck(false);
            }
        }

        requestByFilter();
    }

    private void setOrientationCheckBoxList(HampaCheckBox orientationCheckBox)
    {
        for (HampaCheckBox checkBox : orientationCheckBoxList)
        {
            if (checkBox == orientationCheckBox)
            {
                if (!checkBox.isChecked())
                {
                    checkBox.setCheck(false);
                }
                else
                {
                    checkBox.setCheck(true);
                }
            }
            else
            {
                checkBox.setCheck(false);
            }
        }

        requestByFilter();
    }


    private void requestByFilter()
    {
        String gradeUrl       = getGradeChecked() != null ? getGradeChecked().getValue() : null;
        String orientationUrl = getOrientationChecked() != null ? getOrientationChecked().getValue() : null;

        final HashMap<String, String> data = new HashMap<>();

        if (gradeUrl != null)
        {
            data.put("gradeUrl", gradeUrl);
        }

        if (orientationUrl != null)
        {
            data.put("orientationUrl", orientationUrl);
        }

        recycler.reset();

        recycler.setOnFetch(new LessonExamRecycler.OnFetch()
        {
            @Override
            public void onFetch(final LessonExamRecycler.OnLoadMore loadMore, int page)
            {


                Call<LessonExamList> examListCall = getService().lessonExams(page, data);

                examListCall.enqueue(new Callback<LessonExamList>()
                {
                    @Override
                    public void onResponse(Call<LessonExamList> call, Response<LessonExamList> response)
                    {
                        Response<LessonExamList> res  = response;
                        LessonExamList           body = res.body();

                        Log.i("lessonExamRes", call.request().url() + "");

                        if (body.status.equals("OK"))
                        {

                            List<LessonExam> examList = body.dataList.data;

                            if (examList != null || !examList.isEmpty())
                            {
                                loadMore.loadMore(body.dataList.data);
                            }
                            else
                            {
                                loadMore.complete();
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), "loading is complete", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<LessonExamList> call, Throwable t)
                    {
                        Log.i("lessonExamResError", t.toString());
                    }
                });
            }
        });

        recycler.load();

    }

}
