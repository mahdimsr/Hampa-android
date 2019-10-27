package ir.vanda.hampa.model;

import java.util.List;

public class Cart {

    //Attributes
    Integer id,lessonExamId,transactionId,studentId;

    //Relations
    public Student student;
    public Transaction transaction;
    public LessonExam lessonExam;
}
