package ir.vanda.hampa.model;

import androidx.annotation.NonNull;

import java.util.List;

public class Student {

   //Attributes
   public String  name,familyName,nationalCode,password,mobile,email,average,school,address,telePhone,parentPhone,mobileToken,emailToken,profileImage,remember_token;
   public Integer mobileVerified,emailVerified,isActive,isComplete;
   public Integer id,orientationId,gradeId,cityId;
   public Float   wallet;

   //api auth
   public String access_token;

   //Relations
   public Grade grade;
   public Orientation orientation;
   public City city;
   public Scholarship scholarship;
   public List<Transaction> transactions;
   public List<Cart> carts;


   @NonNull
   @Override
   public String toString()
   {
      return "id: " + id + " mobile: " + mobile;
   }
}
