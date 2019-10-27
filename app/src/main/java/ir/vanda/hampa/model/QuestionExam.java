package ir.vanda.hampa.model;

import java.util.List;

public class QuestionExam {

    //Attributes
    Integer id,questionId,examId;
    String  type;

    //Relations
    public LessonExam lessonExam;
    public Question question;
}
