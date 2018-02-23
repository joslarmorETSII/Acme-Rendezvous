package controllers;

import controllers.AbstractController;
import domain.Rendezvous;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.RendezvousService;
import services.UserService;

import java.util.Date;

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
        result.addObject("cancelUri","cancelAll");
        result.addObject("now",new Date());

        return result;
    }

    @RequestMapping(value = "/listAll-2", method = RequestMethod.GET)
    public ModelAndView listAll2() {
        ModelAndView result;
        result = new ModelAndView("rendezvous/listAll");
        result.addObject("rendezvous", rendezvousService.findAll());

        result.addObject("requestUri","rendezvous/listAll-2.do");

        result.addObject("now",new Date());

        return result;
    }

    // Display ----------------------------------------------------------------

    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int rendezvousId) {
        ModelAndView result;
        Rendezvous rendezvous;

        rendezvous = this.rendezvousService.findOne(rendezvousId);
        result = new ModelAndView("rendezvous/display");
        result.addObject("rendezvous", rendezvous);
        result.addObject("cancelURI", "rendezvous/listAll.do");

        return result;
    }
}
