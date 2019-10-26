package ir.vanda.hampa.model;

public class Scholarship {

    //Attributes
    Integer id,studentId,adminId;
    String  url,status,stdMessage,adminMessage,verifyImage;

    //Relations
    public Student student;
    public Admin admin;
}
