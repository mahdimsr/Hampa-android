package ir.vanda.hampa.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable
{

    //Attributes
    public Integer id,gradeLessonId,answer,hardness;
    public String  questionType,text,optionOne,optionTwo,optionThree,optionFour,description,photo;

    //Relations
    public List<QuestionExam> questionExams;
    public GradeLesson gradeLesson;
}
