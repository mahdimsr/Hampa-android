package ir.vanda.hampa.retrofit;


import androidx.annotation.NonNull;

import ir.vanda.hampa.model.Student;

public class Index
{
    public String    status;
    public Integer   cartCount;
    public Student   student;
    public ItemPhoto itemPhoto;


    public class ItemPhoto
    {
        public String lessonExam, giftExam, generalExam, scholarship, onlineClass, meAndThe, books, untilKonkur, teacher, discussion, game;
    }

    @NonNull
    @Override
    public String toString()
    {
        return "status: " + status + " student: " + student.toString();
    }
}
