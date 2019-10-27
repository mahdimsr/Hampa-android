package ir.vanda.hampa.model;

import java.util.List;

public class Lesson {

    //Attributes
    Integer id,parentId;
    String  code,title,url;

    //Relations
    public List<GradeLesson> gradeLessons;
}
