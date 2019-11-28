package ir.vanda.hampa.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.vanda.hampa.R;
import ir.vanda.hampa.lib.Converter;
import ir.vanda.hampa.model.Question;

/**
 * TODO: document your custom view class.
 */
public class QuestionView extends RelativeLayout
{

    private Question question;

    private RelativeLayout textLayout, photoLayout;
    private LinearLayout  contentLayout;
    private VandaTextView questionText, questionCounter;
    private VandaTextView optionOne, optionTwo, optionThree, optionFour;
    private Integer             answer;
    private List<VandaTextView> optionsList;

    public QuestionView(Context context)
    {
        super(context);
        init(null, 0);
    }

    public QuestionView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public QuestionView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void infalte()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.component_question, this);

        optionsList = new ArrayList<>();

        textLayout    = findViewById(R.id.questionTextLayout);
        photoLayout   = findViewById(R.id.photoLayout);
        contentLayout = findViewById(R.id.contentLayout);

        questionText    = findViewById(R.id.questionText);
        questionCounter = findViewById(R.id.questionCounter);

        optionOne = findViewById(R.id.optionOne);
        optionsList.add(optionOne);
        optionOne.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAnswer(1);
            }
        });

        optionTwo = findViewById(R.id.optionTwo);
        optionsList.add(optionTwo);
        optionTwo.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAnswer(2);
            }
        });

        optionThree = findViewById(R.id.optionThree);
        optionsList.add(optionThree);
        optionThree.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAnswer(3);
            }
        });

        optionFour = findViewById(R.id.optionFour);
        optionsList.add(optionFour);
        optionFour.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                setAnswer(4);
            }
        });
    }


    private void init(AttributeSet attrs, int defStyle)
    {
        //inflate
        infalte();


        // Load attributes
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QuestionView, defStyle, 0);


        typedArray.recycle();

    }

    public void setQuestion(Question question)
    {
        this.question = question;

        questionText.setText(question.text);

        optionOne.setText(question.optionOne);
        optionTwo.setText(question.optionTwo);
        optionThree.setText(question.optionThree);
        optionFour.setText(question.optionFour);

        if (question.photo == null)
        {
            photoLayout.setVisibility(GONE);
        }

        setAnswer(null);

    }


    public void setNumber(int number)
    {
        questionCounter.setText(number + "");
    }


    private void setAnswer(Integer answer)
    {
        this.answer = answer;

        if (answer == null)
        {
            for (int i = 0; i < optionsList.size(); i++)
            {
                VandaTextView option = optionsList.get(i);

                option.setBackground(null);

            }
        }
        else
        {

            Toast.makeText(getContext(), "" + answer, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < optionsList.size(); i++)
            {
                VandaTextView option = optionsList.get(i);

                if (i + 1 == answer)
                {
                    option.setBackgroundResource(R.drawable.bg_question_answer);
                }
                else
                {
                    option.setBackground(null);
                }
            }
        }

    }


    public Integer getAnswer()
    {
        return answer;
    }

    public void setPaddingFromHolder()
    {

        int height        = textLayout.getMeasuredHeight();
        int heightCounter = questionCounter.getMeasuredHeight();

        contentLayout.setPadding(0, height + Converter.dpToPx(getContext().getResources().getDimension(R.dimen.doubleSpace), getContext()), 0, 0);
        contentLayout.requestLayout();


        Log.i("paddingTop", contentLayout.getPaddingTop() + "");

    }


}
