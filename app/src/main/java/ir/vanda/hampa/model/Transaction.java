package ir.vanda.hampa.model;

public class Transaction {

    //Attributes
    Integer id,studentId,itemId,discountId;
    Float   price,discountPrice;
    String  type,itemTyoe,code,status;

    //Relations
    public Student student;
    public LessonExam lessonExam;
    public Discount discount;
}

