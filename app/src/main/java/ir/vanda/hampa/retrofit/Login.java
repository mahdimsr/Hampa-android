package ir.vanda.hampa.retrofit;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import ir.vanda.hampa.model.Student;

public class Login
{
    public String status, access_token, token_type, expires_at;
    public String     errorMessage;
    public Student    student;
    public JsonObject errors;
}
