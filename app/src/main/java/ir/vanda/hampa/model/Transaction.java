package ir.vanda.hampa.model;

public class Transaction
{

    //Attributes
    public Integer id, studentId, itemId, discountId;
    public Float price, discountPrice;
    public String type, itemTyoe, code, status,persian_itemType;

    //Relations
    public Student    student;
    public LessonExam lessonExam;
    public Discount   discount;
}

