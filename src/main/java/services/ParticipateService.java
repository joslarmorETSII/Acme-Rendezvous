package services;

import domain.*;
import forms.QuestionsForm;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.ParticipateRepository;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ParticipateService {

    // Managed Repository -----------------------------------------------------
    @Autowired
    private ParticipateRepository participateRepository;

    // Supporting services ----------------------------------------------------
    @Autowired
    private UserService userService;

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    // Constructors -----------------------------------------------------------
    public ParticipateService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------
    public Participate create(){
        Participate participate;
        User principal;

        principal = userService.findByPrincipal();
        participate = new Participate();
        participate.setAttendant(principal);

        return participate;
    }

    public Participate findOne(Integer participateId){
        return participateRepository.findOne(participateId);
    }

    public Collection<Participate> findAll(){
        return participateRepository.findAll();
    }

    public Participate save(Participate participate){
        Date currentDate = new Date();
        Assert.notNull(participate);
        checkByPrincipal(participate);
        checkMayorEdad(participate.getAttendant());
        //Assert.isTrue(currentDate.before(participate.getMoment()));
        participate.setMoment(currentDate);

        return participateRepository.save(participate);
    }

    public void delete(Participate participate){
        Assert.notNull(participate);
        checkByPrincipal(participate);
        Assert.isTrue(participate.getRendezvous().getMoment().after(participate.getMoment()));
        if(participate.getAttendant().equals(participate.getRendezvous().getCreator())){
            rendezvousService.delete(participate.getRendezvous());
        }

        participateRepository.delete(participate);
    }

    public void deleteParticipated(Rendezvous rendezvous){
        this.participateRepository.delete(rendezvous.getParticipated());
    }

    // Other business methods -------------------------------------------------
    private void checkByPrincipal(Participate participate) {
        User principal = userService.findByPrincipal();
        Assert.isTrue(principal.equals(participate.getAttendant()));
    }

    public Rendezvous reconstruct(QuestionsForm questionsForm, String[] answers,BindingResult binding) {
        Rendezvous result;
        FieldError error;
        String[] codigos;
        List<Question> questions = new ArrayList<>(questionsForm.getQuestions());
        List<Answer> allAnswer = new ArrayList<>();
        questions = new ArrayList<>(questionsForm.getQuestions());

        for(String s:answers){
            Answer userAnswer = answerService.create();
            userAnswer.setAnswer(s);
            allAnswer.add(userAnswer);
        }
        try {
            answerService.saveAnswers(allAnswer);
        }catch(Throwable oops){
                codigos = new String[1];
                codigos[0] = "user.password.mismatch";
                error= new FieldError("questionsForm","answer", questions,false,codigos,null,"");
                binding.addError(error);
        }
        for(int i =0;i<questions.size();i++){
            questions.get(i).getAnswers().add(allAnswer.get(i));
        }
        questionService.saveAll(questions);
        result = questions.get(0).getRendezvous();
        return result;
    }
    public Participate participate(int attendantId,int rendezvousId){
        Participate participate =participateRepository.participate(attendantId,rendezvousId);
        return participate;
    }

    public void checkMayorEdad(User attendant){
        Date fechaActual= new Date();
        Integer edad=  fechaActual.getYear()-attendant.getBirthday().getYear();
        Assert.isTrue(edad>=18);
    }


}
