package ir.vanda.hampa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vanda.hampa.R;
import ir.vanda.hampa.component.VandaTextView;
import ir.vanda.hampa.model.LessonExam;

public class MyExamAdapter extends RecyclerView.Adapter<MyExamAdapter.Holder>
{
    private List<LessonExam>    examList;
    private Context             context;
    private OnItemClickListener onItemClickListener;

    public MyExamAdapter(Context context, List<LessonExam> examList)
    {
        this.examList = examList;
        this.context  = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.viewholder_my_lesson_exam, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {
        final LessonExam lessonExam = examList.get(position);

        holder.title.setText(lessonExam.title);

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onItemClickListener != null)
                {
                    onItemClickListener.onClick(lessonExam);
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return examList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        public VandaTextView title;

        public Holder(@NonNull View itemView)
        {
            super(itemView);

            title = itemView.findViewById(R.id.title);

        }
    }


    public interface OnItemClickListener
    {
        void onClick(LessonExam lessonExam);
    }
}
