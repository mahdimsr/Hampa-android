package ir.vanda.hampa.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class GiftExam {

    //Attributes
    Integer   id,duration;
    String    exm,title,description,answerSheet;
    Timestamp activeTime;
    Date      resultDate;

    //Relations
    public List<GradeLesson> gradeLessons;
    public List<ExamGradeLesson> examGradeLessons;
    public List<QuestionExam> questionExams;
    public List<Question> questions;
}
