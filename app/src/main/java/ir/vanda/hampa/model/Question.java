package ir.vanda.hampa.model;

import java.util.List;

public class Question {

    //Attributes
    Integer id,gradeLessonId,answer,hardness;
    String  questionType,text,optionOne,optionTwo,optionThree,optionFour,description,photo;

    //Relations
    public List<QuestionExam> questionExams;
    public GradeLesson gradeLesson;
}
