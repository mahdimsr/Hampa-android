package ir.vanda.hampa.model;

import java.util.List;

public class Orientation {

    //Attributes
    Integer id,code;
    String  title,url;

    //Relations
    public List<GradeLesson> gradeLessons;
}
