package ir.vanda.hampa.model;

import java.util.List;

public class Student {

   //Attributes
   String  name,familyName,nationalCode,password,mobile,email,average,school,address,telePhone,parentPhone,mobileToken,emailToken,profileImage,remember_token;
   Boolean mobileVerified,emailVerified,isActive,isComplete;
   Integer id,orientationId,gradeId,cityId;
   Float   wallet;

   //Relations
   public Grade grade;
   public Orientation orientation;
   public City city;
   public Scholarship scholarship;
   public List<Transaction> transactions;
   public List<Cart> carts;
}
