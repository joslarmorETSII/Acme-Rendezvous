package controllers;

import domain.Answer;
import domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.AnswerService;
import services.QuestionService;
import services.RendezvousService;

@Controller
@RequestMapping("/answer/user")
public class AnswerUserController  extends AbstractController {

    // Services --------------------------------------------
    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RendezvousService rendezvousService;

    // Constructor -----------------------------------------
    public AnswerUserController() {
        super();
    }

    // Creation --------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Answer answer;

        answer = answerService.create();
        result = createEditModelAndView(answer);
        return result;
    }

    private ModelAndView createEditModelAndView(Answer answer) {
        return null;
    }
}
