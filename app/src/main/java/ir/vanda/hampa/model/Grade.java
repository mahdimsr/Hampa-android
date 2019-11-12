package ir.vanda.hampa.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Grade
{

    //Attributes
    public Integer id, code;
    public String title, url;

    // Relations
    public List<Student>     students;
    public List<GradeLesson> gradeLessons;

    @NonNull
    @Override
    public String toString()
    {
        return "id: " + id + " title: " + title;
    }
}
