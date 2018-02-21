
package forms;

import domain.Answer;
import domain.Question;

import javax.validation.Valid;
import java.util.Collection;


public class QuestionsForm {


    public QuestionsForm() {
        super();
    }

    private Answer answer0;
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;
    private Answer answer4;
    private Collection<Question> questions;

    public Collection<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Collection<Question> questions) {
        this.questions = questions;
    }

    @Valid
    public Answer getAnswer0() {
        return answer0;
    }
    public void setAnswer0(Answer answer0) {
        this.answer0 = answer0;
    }
    @Valid
    public Answer getAnswer1() {
        return answer1;
    }
    public void setAnswer1(Answer answer1) {
        this.answer1 = answer1;
    }
    @Valid
    public Answer getAnswer2() {
        return answer2;
    }
    public void setAnswer2(Answer answer2) {
        this.answer2 = answer2;
    }
    @Valid
    public Answer getAnswer3() {
        return answer3;
    }
    public void setAnswer3(Answer answer3) {
        this.answer3 = answer3;
    }
    @Valid
    public Answer getAnswer4() {
        return answer4;
    }

    public void setAnswer4(Answer answer4) {
        this.answer4 = answer4;
    }
}
