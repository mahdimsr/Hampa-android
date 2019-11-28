package ir.vanda.hampa.model;

import java.io.Serializable;
import java.util.List;

public class Lesson implements Serializable
{

    //Attributes
    public Integer id,parentId;
    public String  code,title,url;

    //Relations
    public List<GradeLesson> gradeLessons;
}
