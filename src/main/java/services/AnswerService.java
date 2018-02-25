package services;

import domain.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AnswerRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AnswerService {

    // Managed repository -----------------------------------------------------
    @Autowired
    private AnswerRepository answerRepository;

    // Supporting services ----------------------------------------------------

    // Constructors -----------------------------------------------------------
    public AnswerService(){super();}

    // Simple CRUD methods ----------------------------------------------------

    public Answer create(){
        Answer answer;

        answer = new Answer();

        return answer;
    }

    public Answer findOne(Integer answerId){
        return answerRepository.findOne(answerId);
    }

    public Collection<Answer> findAll(){
        return answerRepository.findAll();
    }

    public Answer save(Answer answer){
        return answerRepository.save(answer);
    }

    public void delete(Answer answer){
    }
    // Other business methods -------------------------------------------------
    public void saveAnswers(Collection<Answer> answers){
        answerRepository.save(answers);
    }
}
