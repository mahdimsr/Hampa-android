package ir.vanda.hampa.model;

import java.util.List;

public class Grade {

    //Attributes
    Integer id,code;
    String  title,url;

    // Relations
    public List<Student> students;
    public List<GradeLesson> gradeLessons;

}
