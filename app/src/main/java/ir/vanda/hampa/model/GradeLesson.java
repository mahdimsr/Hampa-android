package ir.vanda.hampa.model;

import java.util.List;

public class GradeLesson
{

    //Attributes
    public Integer id, gradeId, orientationId, lessonId, ratio;
    public String sort, code;

    //Relations
    public Grade                 grade;
    public Orientation           orientation;
    public Lesson                lesson;
    public List<Question>        questions;
    public List<ExamGradeLesson> examGradeLessons;
}
