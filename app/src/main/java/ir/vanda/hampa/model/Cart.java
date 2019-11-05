package ir.vanda.hampa.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Cart
{

    //Attributes
    public Integer id, lessonExamId, transactionId, studentId;

    //Relations
    public Student     student;
    public Transaction transaction;
    public LessonExam  lesson_exam;

    @NonNull
    @Override
    public String toString()
    {
        return "id: " + id + " lessonExm: " + lesson_exam.exm;
    }
}
