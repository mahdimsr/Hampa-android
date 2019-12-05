package ir.vanda.hampa.model;

public class Result
{
    public Integer id, studentId, examId, blankAnswers, correctAnswers, wrongAnswers;

    public String type, status, persianCreatedAt;


    public Integer getQuestionCount()
    {
        return blankAnswers + correctAnswers + wrongAnswers;
    }
}
