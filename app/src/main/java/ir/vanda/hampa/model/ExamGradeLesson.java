package ir.vanda.hampa.model;

public class ExamGradeLesson {

    //Attributes
    Integer id,examId,gradeLessonId;
    String  type;

    //Relations
    public LessonExam lessonExam;
    public GradeLesson gradeLesson;
}
