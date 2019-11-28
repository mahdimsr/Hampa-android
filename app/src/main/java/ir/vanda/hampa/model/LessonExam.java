package ir.vanda.hampa.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class LessonExam implements Serializable
{

    //Attributes
    public Integer id, duration, questionCount;
    public String exm, title, description, answerSheet, status, persianCreatedAt;
    public Float   price;
    public Boolean isPublic;
    public Date    activeDate;

    //Relations
    public List<ExamCode>        examCodes;
    public List<Discount>        discounts;
    public List<ExamGradeLesson> examGradeLessons;
    public List<GradeLesson>     gradeLessons;
    public List<QuestionExam>    questionExams;
    public List<Question>        questions;
    public List<Grade>           grades;
    public Orientation           orientations;
    public List<Lesson>          lessons;


    @NonNull
    @Override
    public String toString()
    {
        return "id: " + id + " exm: " + exm;
    }
}
