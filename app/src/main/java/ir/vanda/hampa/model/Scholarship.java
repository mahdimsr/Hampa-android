package ir.vanda.hampa.model;

public class Scholarship {

    //Attributes
    public Integer id,studentId,adminId;
    public String  url,status,stdMessage,adminMessage,verifyImage,persian_status;

    //Relations
    public Student student;
    public Admin admin;
}
