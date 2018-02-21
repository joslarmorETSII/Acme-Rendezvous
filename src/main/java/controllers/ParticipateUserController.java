package controllers;

import domain.Answer;
import domain.Participate;
import domain.Rendezvous;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.LoginService;
import services.*;

import java.util.Collection;

@Controller
@RequestMapping("/participate/user")
public class ParticipateUserController  extends AbstractController {

    // Services --------------------------------------------
    @Autowired
    private AnswerService answerService;

    @Autowired
    private ParticipateService participateService;

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
    public ModelAndView create(@RequestParam int rendezvousId) {
        ModelAndView result;
        Rendezvous rendezvous;
        Participate participate;

        participate = participateService.create();
        rendezvous = rendezvousService.findOne(rendezvousId);
        participate.setRendezvous(rendezvous);
       // if(rendezvous.getQuestions().size()>0)
          //result = createModelAndView(rendezvous)
        participateService.save(participate);
        rendezvous.getParticipated().add(participate);
        result = new ModelAndView("redirect: ../../rendezvous/listAll.do");
        result.addObject("rendezvous",rendezvousService.findAll());
        result.addObject("user",userService.findByPrincipal());

        return result;
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public ModelAndView cancel(@RequestParam int rendezvousId){
        ModelAndView result ;
        Rendezvous rendezvous;
        Participate participate;
        User attendant= userService.findByPrincipal();
        participate=participateService.participate(attendant.getId(),rendezvousId);
        participateService.delete(participate);
        result = new ModelAndView("redirect: ../../rendezvous/listAll.do");
        result.addObject("rendezvous",rendezvousService.findAll());
        result.addObject("user",userService.findByPrincipal());

        return result;

    }






}
