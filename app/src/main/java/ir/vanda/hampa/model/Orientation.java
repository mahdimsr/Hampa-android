package ir.vanda.hampa.model;

import java.io.Serializable;
import java.util.List;

public class Orientation implements Serializable
{

    //Attributes
    public Integer id,code;
    public String  title,url;

    //Relations
    public List<GradeLesson> gradeLessons;
}
