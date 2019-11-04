package ir.vanda.hampa.model;

import java.util.List;

public class Lesson {

    //Attributes
    public Integer id,parentId;
    public String  code,title,url;

    //Relations
    public List<GradeLesson> gradeLessons;
}
