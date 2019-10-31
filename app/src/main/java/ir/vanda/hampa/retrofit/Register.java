package ir.vanda.hampa.retrofit;

import com.google.gson.JsonObject;

import ir.vanda.hampa.model.Student;

public class Register
{
    public String status, access_token, token_type;
    public String     errorMessage;
    public Student    student;
    public JsonObject errors;
}
