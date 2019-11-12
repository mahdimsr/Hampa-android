package ir.vanda.hampa.retrofit;


import androidx.annotation.NonNull;

import ir.vanda.hampa.model.Student;

public class Index
{
    public String  status;
    public Integer cartCount;
    public Student student;


    @NonNull
    @Override
    public String toString()
    {
        return "status: " + status + " student: " + student.toString();
    }
}
