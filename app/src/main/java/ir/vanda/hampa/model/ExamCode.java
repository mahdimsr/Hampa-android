package ir.vanda.hampa.model;

import java.io.Serializable;

public class ExamCode implements Serializable
{

    //Attributes
    Integer id,examId,discountId;

    //Relations
    public Discount discount;
}
