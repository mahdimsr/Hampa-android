package ir.vanda.hampa.model;

import java.util.List;

public class Orientation {

    //Attributes
    public Integer id,code;
    public String  title,url;

    //Relations
    public List<GradeLesson> gradeLessons;
}
