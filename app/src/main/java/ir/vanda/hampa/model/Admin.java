package ir.vanda.hampa.model;

import java.util.List;

public class Admin {

    //Attributes
    String  fullName,username,password,level,remember_token;
    Integer id,parentId;

    //Relations
    public List<Scholarship> scholarships;
    public List<Admin> admins;
}
