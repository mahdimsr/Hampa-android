package ir.vanda.hampa.retrofit;

import java.util.List;

import ir.vanda.hampa.model.LessonExam;

public class LessonExamList
{
    public String   status;
    public DataList dataList;


    public class DataList
    {
        public int              current_page;
        public List<LessonExam> data;
    }
}
