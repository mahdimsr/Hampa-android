package ir.vanda.hampa.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Discount implements Serializable
{

    //Attributes
    public Integer id, value, count;
    public String code, type,persianEndDate,persianType;
    public Date endDate;

    //Relations
    public List<StudentCode> studentCodes;
    public List<ExamCode>    examCodes;

}
