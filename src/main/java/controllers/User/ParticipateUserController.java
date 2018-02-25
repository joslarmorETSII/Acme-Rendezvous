package controllers.User;

import controllers.AbstractController;
import domain.*;
import forms.QuestionsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/participate/user")
public class ParticipateUserController  extends AbstractController {

    // Services --------------------------------------------
    @Autowired
    private AnswerService answerService;

    @Autowired
    private ParticipateService participateService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RendezvousService rendezvousService;

    // Constructor -----------------------------------------
    public ParticipateUserController() {
        super();
    }

    // Creation --------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int rendezvousId,@RequestParam(required= false) String message) {
        ModelAndView result;
        Rendezvous rendezvous;
        Participate participate;

        participate = participateService.create();
        rendezvous = rendezvousService.findOne(rendezvousId);
        participate.setRendezvous(rendezvous);
        if (rendezvous.getQuestions().size() > 0) {
            QuestionsForm questionsForm = new QuestionsForm();
            questionsForm.setQuestions(rendezvous.getQuestions());
            questionsForm.setAnswer(answerService.create());
            result = new ModelAndView("rendezvous/answerQuestion");
            result.addObject("questionForm", questionsForm);
            result.addObject("questions",rendezvous.getQuestions());
            result.addObject("message",message);
        } else {
            participateService.save(participate);
            rendezvous.getParticipated().add(participate);
            result = new ModelAndView("redirect: ../../rendezvous/listAll.do");
            result.addObject("rendezvous", rendezvousService.findAll());
            result.addObject("user", userService.findByPrincipal());
        }
        return result;
    }

    @RequestMapping(value = "/answerQuestion", method = RequestMethod.POST)
    public ModelAndView answerQuestion(@Valid  QuestionsForm questionsForm, BindingResult binding, HttpServletRequest request) {
        ModelAndView result;
        Rendezvous rendezvous;
        String[] answers = request.getParameterValues("answer");

        if (binding.hasErrors() )
            result = createEditModelAndViewForm(questionsForm, "question.save.error");
        else
            try {
                rendezvous = participateService.reconstruct(questionsForm, answers, binding);
                Participate participate = participateService.create();
                participate.setRendezvous(rendezvous);
                participateService.save(participate);
                rendezvous.getParticipated().add(participate);
                result = new ModelAndView("redirect: ../../rendezvous/listAll.do");
                result.addObject("rendezvous", rendezvousService.findAll());
                result.addObject("user", userService.findByPrincipal());
            } catch (final Throwable oops) {
                result = createQuestionForm(questionsForm,questionsForm.getQuestions().iterator().next().getRendezvous(),"question.save.error");
            }
        return result;
    }


    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public ModelAndView cancel(@RequestParam int rendezvousId) {
        ModelAndView result;
        User user;
        Participate participate;

        user = userService.findByPrincipal();
        participate = participateService.participate(user.getId(),rendezvousId);
        participateService.delete(participate);
        result = new ModelAndView("redirect: ../../rendezvous/user/list.do");
        result.addObject("rendezvous",rendezvousService.findAll());
        result.addObject("user",user);

        return result;
    }

    @RequestMapping(value = "/cancelAll", method = RequestMethod.GET)
    public ModelAndView cance2(@RequestParam int rendezvousId) {
        ModelAndView result;
        User user;
        Participate participate;

        user = userService.findByPrincipal();
        participate = participateService.participate(user.getId(),rendezvousId);

        result = new ModelAndView("redirect: ../../rendezvous/listAll.do");
        result.addObject("rendezvous",user.getRendezvouses());
        result.addObject("user",user);
        result.addObject("cancelUri","cancelAll");
        try {
            participateService.delete(participate);
        }catch (Throwable oops){
            return result;
        }

        return result;
    }

        protected ModelAndView createEditModelAndViewForm(QuestionsForm questionsForm, String message) {

        ModelAndView result = new ModelAndView("rendezvous/answerQuestion");

        result.addObject("questionForm", questionsForm);
        result.addObject("questions",questionsForm.getQuestions());
        result.addObject("message", message);
        return result;
    }

    protected ModelAndView createModelAndview(Question question) {
        ModelAndView result;

        result = this.createEditModelAndView(question, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Question question, final String messageCode) {
        ModelAndView result;

        result = new ModelAndView("rendezvous/edit");
        result.addObject("question", question);

        result.addObject("message", messageCode);
        return result;
    }
    protected ModelAndView createQuestionForm(QuestionsForm questionsForm,Rendezvous rendezvous,String message){
        ModelAndView result;


        result = new ModelAndView("rendezvous/answerQuestion");
        result.addObject("questionForm", questionsForm);
        result.addObject("questions",rendezvous.getQuestions());
        result.addObject("message",message);

        return result;
    }
}
