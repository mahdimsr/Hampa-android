package ir.vanda.hampa.model;

import java.io.Serializable;

public class ExamGradeLesson implements Serializable
{

    //Attributes
    Integer id,examId,gradeLessonId;
    String  type;

    //Relations
    public LessonExam lessonExam;
    public GradeLesson gradeLesson;
}
