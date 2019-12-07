package ir.vanda.hampa.fragment;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.vanda.hampa.BaseFragment;
import ir.vanda.hampa.R;
import ir.vanda.hampa.component.HampaDialog;
import ir.vanda.hampa.component.RoundedImageView;
import ir.vanda.hampa.fragment.mainChild.LessonExamsFragment;
import ir.vanda.hampa.fragment.profileChild.ScholarshipFragment;
import ir.vanda.hampa.model.Cart;
import ir.vanda.hampa.retrofit.Index;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener
{


    public MainFragment()
    {
        // Required empty public constructor
    }

    private RelativeLayout lessonExamLayout, scholarshipsLayout, giftExamLayout, generalExamLayout, helper_booksLayout, onlineClassLayout, meAndAdviserLayout;
    private RelativeLayout discussionLayout, toKonkurLayout, teacher_introduceLayout, funLayout;
    private RoundedImageView lessonExamImage, scholarshipsImage, giftExamImage, generalExamImage, helper_booksImage, onlineClassImage, meAndAdviserImage;
    private RoundedImageView discussionImage, toKonkurImage, teacher_introduceImage, funImage;


    private LessonExamsFragment lessonExamsFragment;
    private ScholarshipFragment scholarshipFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        findView(v);

        lessonExamsFragment = new LessonExamsFragment();
        scholarshipFragment = new ScholarshipFragment();

        Call<Index> indexCall = getService().index();

        indexCall.enqueue(new Callback<Index>()
        {
            @Override
            public void onResponse(Call<Index> call, Response<Index> response)
            {
                Response<Index> res   = response;
                Index           index = res.body();


                if (res.isSuccessful())
                {
                    Log.i("indexRes", index.toString() + "");

                    Integer cartCount = index.cartCount;

                    if (cartCount != null && cartCount != 0)
                    {
                        setCartCount(cartCount);
                    }

                    Picasso.get().load(index.itemPhoto.lessonExam).into(lessonExamImage);
                    Picasso.get().load(index.itemPhoto.scholarship).into(scholarshipsImage);
                    Picasso.get().load(index.itemPhoto.giftExam).into(giftExamImage);
                    Picasso.get().load(index.itemPhoto.generalExam).into(generalExamImage);
                    Picasso.get().load(index.itemPhoto.books).into(helper_booksImage);
                    Picasso.get().load(index.itemPhoto.onlineClass).into(onlineClassImage);
                    Picasso.get().load(index.itemPhoto.discussion).into(discussionImage);
                    Picasso.get().load(index.itemPhoto.meAndThe).into(meAndAdviserImage);
                    Picasso.get().load(index.itemPhoto.untilKonkur).into(toKonkurImage);
                    Picasso.get().load(index.itemPhoto.teacher).into(teacher_introduceImage);
                    Picasso.get().load(index.itemPhoto.game).into(funImage);

                }
                else
                {

                }

            }

            @Override
            public void onFailure(Call<Index> call, Throwable t)
            {
                Log.i("indexResError", t.toString());
            }
        });


        return v;
    }

    private void findView(View v)
    {
        lessonExamImage  = v.findViewById(R.id.lessonExamImage);
        lessonExamLayout = v.findViewById(R.id.lessonExamLayout);
        lessonExamLayout.setOnClickListener(this);

        scholarshipsImage  = v.findViewById(R.id.scholarshipsImage);
        scholarshipsLayout = v.findViewById(R.id.scholarshipsLayout);
        scholarshipsLayout.setOnClickListener(this);

        giftExamImage  = v.findViewById(R.id.giftExamImage);
        giftExamLayout = v.findViewById(R.id.giftExamLayout);
        giftExamLayout.setOnClickListener(this);

        generalExamImage  = v.findViewById(R.id.generalExamImage);
        generalExamLayout = v.findViewById(R.id.generalExamLayout);
        generalExamLayout.setOnClickListener(this);

        helper_booksImage  = v.findViewById(R.id.helper_booksImage);
        helper_booksLayout = v.findViewById(R.id.helper_booksLayout);
        helper_booksLayout.setOnClickListener(this);

        onlineClassImage  = v.findViewById(R.id.onlineClassImage);
        onlineClassLayout = v.findViewById(R.id.onlineClassLayout);
        onlineClassLayout.setOnClickListener(this);

        meAndAdviserImage  = v.findViewById(R.id.meAndAdviserImage);
        meAndAdviserLayout = v.findViewById(R.id.meAndAdviserLayout);
        meAndAdviserLayout.setOnClickListener(this);

        discussionImage  = v.findViewById(R.id.discussionImage);
        discussionLayout = v.findViewById(R.id.discussionLayout);
        discussionLayout.setOnClickListener(this);

        toKonkurImage  = v.findViewById(R.id.toKonkurImage);
        toKonkurLayout = v.findViewById(R.id.toKonkurLayout);
        toKonkurLayout.setOnClickListener(this);

        teacher_introduceImage  = v.findViewById(R.id.teacher_introduceImage);
        teacher_introduceLayout = v.findViewById(R.id.teacher_introduceLayout);
        teacher_introduceLayout.setOnClickListener(this);

        funImage  = v.findViewById(R.id.funImage);
        funLayout = v.findViewById(R.id.funLayout);
        funLayout.setOnClickListener(this);
    }


    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        if (!hidden)
        {
            bottomMenuAnimate("show");
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lessonExamLayout:

                bottomMenuAnimate("hide");
                showFragment(lessonExamsFragment, "lessonExamFragment");

                break;

            case R.id.scholarshipsLayout:

                bottomMenuAnimate("hide");
                showFragment(scholarshipFragment,"scholarshipFragment");

                break;

            default:

                HampaDialog dialog = new HampaDialog(getContext());
                dialog.show();

                dialog.setValues(R.drawable.ic_access_denied, "هوپس", "وقت بخیر، ببخشید هنوز این قسمت تکمیل نشده و داریم روش کار میکنیم به محض اینکه آماده شد بهت خبر میدیم.", "باشه", null);
                dialog.setOnDefaultClickLisetener(new HampaDialog.OnDefaultClickListener()
                {
                    @Override
                    public void onClick(Dialog dialog)
                    {
                        dialog.dismiss();
                    }
                });
        }
    }
}
