package ir.vanda.hampa.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.vanda.hampa.R;
import ir.vanda.hampa.model.LessonExam;

public class LessonExamRecycler extends RecyclerView
{

    private boolean isLoading = false, isComplete = false;
    private int                 page = 1;
    private List<LessonExam>    itemList;
    private SelfAdapter         adapter;
    private OnFetch             onFetch;
    private OnItemClickListener onItemClickListener;

    public LessonExamRecycler(@NonNull Context context)
    {
        super(context);
        config();
    }

    public LessonExamRecycler(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        config();
    }

    public LessonExamRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        config();
    }

    //config
    private void config()
    {
        adapter  = new SelfAdapter();
        itemList = new ArrayList<>();
        adapter.setItems(itemList);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);


        setLayoutManager(layoutManager);
        addItemDecoration(new Decoration());

        setItemAnimator(null);
        setLayoutManager(layoutManager);
        setAdapter(adapter);
    }

    public void reset()
    {
        itemList.clear();
        adapter.notifyDataSetChanged();
        isComplete = false;
        isLoading  = false;
        smoothScrollBy(0, 0);
        page = 1;
    }

    public void setOnFetch(OnFetch onFetch)
    {
        this.onFetch = onFetch;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    //adapter
    private class SelfAdapter extends Adapter<SelfAdapter.ItemHolder>
    {
        private List<LessonExam> examList;

        public void setItems(List<LessonExam> examList)
        {
            this.examList = examList;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position)
        {
            return new ItemHolder(LayoutInflater.from(getContext()).inflate(R.layout.viewholder_lesson_exam, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemHolder itemHolder, int position)
        {

            final LessonExam exam = examList.get(position);

            itemHolder.title.setText(exam.title);
            Picasso.get().load(exam.photo).into(itemHolder.photo);


            itemHolder.itemView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onItemClickListener.OnClick(exam);
                }
            });

        }

        @Override
        public int getItemCount()
        {
            return examList.size();
        }

        class ItemHolder extends ViewHolder
        {
            public VandaTextView    title;
            public RoundedImageView photo;

            public ItemHolder(@NonNull View itemView)
            {
                super(itemView);

                title = itemView.findViewById(R.id.title);

                photo = itemView.findViewById(R.id.photo);

            }
        }
    }

    //decoration
    private class Decoration extends ItemDecoration
    {
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state)
        {
            view.setPadding(15, 15, 15, 15);


        }

        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull State state)
        {
            super.onDraw(c, parent, state);
        }
    }

    //classes and interfaces and functions
    public interface OnLoadMore
    {
        void loadMore(List<LessonExam> examList);

        void complete();
    }


    public interface OnFetch
    {
        void onFetch(OnLoadMore loadMore, int page);
    }

    public interface OnItemClickListener
    {
        void OnClick(LessonExam lessonExam);
    }

    public void load()
    {
        if (itemList.size() == 0)
        {
            fetchData();
        }
    }

    private void fetchData()
    {
        // check load is complete
        if (!isComplete)
        {
            if (!isLoading)
            {
                isLoading = true;


                if (onFetch != null)
                {
                    OnLoadMore onLoadMore = new OnLoadMore()
                    {
                        public void loadMore(List<LessonExam> examList)
                        {
                            itemList.addAll(examList);
                            adapter.notifyItemRangeInserted(itemList.size(), examList.size());


                            isLoading = false;

                            Log.i("proRes", "done fetch");

                            page++;
                        }

                        public void complete()
                        {
                            isComplete = true;


                        }
                    };

                    onFetch.onFetch(onLoadMore, page);
                }

            }
        }

    }

    //do loading on scroll
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt)
    {
        if (computeVerticalScrollRange() - 300 < computeVerticalScrollOffset() + computeVerticalScrollExtent())
        {
            fetchData();
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }

    private class ItemViewHolder extends ViewHolder
    {
        public ItemViewHolder(@NonNull View itemView)
        {
            super(itemView);
        }
    }

}
