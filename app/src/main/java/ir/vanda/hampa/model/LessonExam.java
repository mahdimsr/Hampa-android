package ir.vanda.hampa.model;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

public class LessonExam {

    //Attributes
    public Integer id,duration;
    public String  exm,title,description,answerSheet,status;
    public Float   price;
    public Boolean isPublic;
    public Date    activeDate;

    //Relations
    public List<ExamCode> examCodes;
    public List<Discount> discounts;
    public List<ExamGradeLesson> examGradeLessons;
    public List<GradeLesson> gradeLessons;
    public List<QuestionExam> questionExams;
    public List<Question> questions;


    @NonNull
    @Override
    public String toString()
    {
        return "id: " + id + " exm: " + exm ;
    }
}
