package ir.vanda.hampa.model;

import java.util.List;

public class City {

    //Attributes
    Integer id,provinceId;
    String  name;

    //Relations
    public Province province;
    public List<Student> students;
}
