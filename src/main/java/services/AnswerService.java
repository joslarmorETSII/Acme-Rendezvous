package services;

import domain.Answer;
import domain.User;
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

    @Autowired
    private UserService userService;

    // Supporting services ----------------------------------------------------

    // Constructors -----------------------------------------------------------
    public AnswerService(){super();}

    // Simple CRUD methods ----------------------------------------------------

    public Answer create(){
        Answer answer;
        User user = userService.findByPrincipal();
        answer = new Answer();
        answer.setUser(user);

        return answer;
    }

    public Answer findOne(Integer answerId){
        return answerRepository.findOne(answerId);
    }

    public Collection<Answer> findAll(){
        return answerRepository.findAll();
    }

    public Answer save(Answer answer){
        User user = answer.getUser();
        Answer aux = answerRepository.save(answer);
        user.getAnswers().add(aux);
        userService.save(user);
        return aux;
    }

    public void delete(Answer answer){
    }
    // Other business methods -------------------------------------------------
    public void saveAnswers(Collection<Answer> answers){
        answerRepository.save(answers);
    }

    public Object[] avgDevAnswersQuestionsPerRendezvous() {
        Object[] result;
        result = this.answerRepository.avgDevAnswersQuestionsPerRendezvous();
        return result;
    }
}
