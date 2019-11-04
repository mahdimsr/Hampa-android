package ir.vanda.hampa.model;

import java.util.List;

public class Grade
{

    //Attributes
    public Integer id, code;
    public String title, url;

    // Relations
    public List<Student>     students;
    public List<GradeLesson> gradeLessons;

}
