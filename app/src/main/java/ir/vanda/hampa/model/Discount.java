package ir.vanda.hampa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Discount implements Serializable
{

    //Attributes
    Integer id,value,count;
    String  code,type;
    Date    endDate;

    //Relations
    public List<StudentCode> studentCodes;
    public List<ExamCode> examCodes;

}
