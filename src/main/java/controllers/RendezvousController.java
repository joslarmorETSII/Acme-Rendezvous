package controllers;

import controllers.AbstractController;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.RendezvousService;
import services.UserService;

@Controller
@RequestMapping("/rendezvous")
public class RendezvousController extends AbstractController {
    // Services --------------------------------------------

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;


    // Constructor --------------------------------------------

    public RendezvousController() {
        super();
    }

    // Listing -------------------------------------------------------
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public ModelAndView listAll() {
        ModelAndView result;
        User user;

        user = userService.findByPrincipal();
        result = new ModelAndView("rendezvous/list");
        result.addObject("rendezvous", rendezvousService.findAll());
        result.addObject("user",user);
        result.addObject("requestUri","rendezvous/listAll.do");
        return result;
    }
}
