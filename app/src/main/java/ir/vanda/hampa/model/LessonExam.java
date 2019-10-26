package ir.vanda.hampa.model;

import java.util.Date;
import java.util.List;

public class LessonExam {

    //Attributes
    Integer id,duration;
    String  exm,title,description,answerSheet,status;
    Float   price;
    Boolean isPublic;
    Date    activeDate;

    //Relations
    public List<ExamCode> examCodes;
    public List<Discount> discounts;
    public List<ExamGradeLesson> examGradeLessons;
    public List<GradeLesson> gradeLessons;
    public List<QuestionExam> questionExams;
    public List<Question> questions;

}
