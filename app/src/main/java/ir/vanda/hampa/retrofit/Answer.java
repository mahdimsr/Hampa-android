package ir.vanda.hampa.retrofit;

import androidx.annotation.NonNull;

public class Answer
{
    public Integer id, answer;

    @NonNull
    @Override
    public String toString()
    {
        return "id: " + id + " answer: " + answer;
    }
}
