package ir.vanda.hampa.retrofit;

import ir.vanda.hampa.model.LessonExam;

public class LessonExamQuestions
{
    public String     status;
    public Config     config;
    public LessonExam lessonExam;


    public class Config
    {
        public Integer duration;
        public Boolean isFinished;
    }
}
