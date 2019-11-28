package ir.vanda.hampa.model;

import java.io.Serializable;
import java.util.List;

public class QuestionExam implements Serializable
{

    //Attributes
    Integer id,questionId,examId;
    String  type;

    //Relations
    public LessonExam lessonExam;
    public Question question;
}
