package ir.vanda.hampa.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Grade implements Serializable
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
