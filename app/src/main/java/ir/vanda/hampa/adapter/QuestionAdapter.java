package ir.vanda.hampa.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.vanda.hampa.component.QuestionView;
import ir.vanda.hampa.model.Question;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.Holder>
{
    private List<Question> questionList;
    private Context        context;

    public QuestionAdapter(Context context, List<Question> questionList)
    {
        this.questionList = questionList;
        this.context      = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new Holder(new QuestionView(context));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {
        Question question = questionList.get(position);

        holder.questionView.setNumber(position + 1);
        holder.questionView.setQuestion(question);
        holder.questionView.setPaddingFromHolder();
    }

    @Override
    public int getItemCount()
    {
        return questionList.size();
    }

    public class Holder extends RecyclerView.ViewHolder
    {
        public QuestionView questionView;

        public Holder(@NonNull QuestionView itemView)
        {
            super(itemView);

            questionView = itemView;
        }
    }


}
