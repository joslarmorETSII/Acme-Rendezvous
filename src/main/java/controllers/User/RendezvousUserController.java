package controllers.User;

import controllers.AbstractController;
import domain.Rendezvous;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.RendezvousService;
import services.UserService;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/rendezvous/user")
public class RendezvousUserController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private RendezvousService rendezvousService;

    @Autowired
    private UserService userService;


    // Constructor --------------------------------------------

    public RendezvousUserController() {
        super();
    }

    // Listing -------------------------------------------------------

   @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam(required= false, defaultValue = "0") Integer userId ) {
        ModelAndView result;
        User user;
        Collection<Rendezvous> rendezvouses;

       SimpleDateFormat formatterEs;
       SimpleDateFormat formatterEn;
       String momentEs;
       String momentEn;

       formatterEs = new SimpleDateFormat("dd/MM/yyyy HH:mm");
       momentEs = formatterEs.format(new Date());
       formatterEn = new SimpleDateFormat("yyyy/MM/dd HH:mm");
       momentEn = formatterEn.format(new Date());

        if(userId!=0){
             user=userService.findOne(userId);
            rendezvouses= rendezvousService.userParticipate(user.getId());
        }else {
            user = userService.findByPrincipal();
            rendezvouses = rendezvousService.userParticipate(user.getId());
        }
        result = new ModelAndView("rendezvous/list");
        result.addObject("rendezvous", rendezvouses);
        result.addObject("user",user);
        result.addObject("requestUri","rendezvous/user/list.do");
        result.addObject("cancelUri","cancel");
       result.addObject("now",new Date());
       result.addObject("momentEs", momentEs);
       result.addObject("momentEn", momentEn);

       return result;

    }

    // Creation ------------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        ModelAndView result;
        Rendezvous rendezvous;

        rendezvous = this.rendezvousService.create();
        result = this.createEditModelAndView(rendezvous);

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
        result.addObject("cancelURI", "rendezvous/user/list.do");

        return result;
    }


   //  Edition ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int rendezvousId) {
        final ModelAndView result;
        Rendezvous rendezvous;
        rendezvous = this.rendezvousService.findOne(rendezvousId);
        Assert.notNull(rendezvous);
        result = this.createEditModelAndView(rendezvous);
        return result;
    }



    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid  Rendezvous rendezvous, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(rendezvous);
        else
            try {
                this.rendezvousService.save(rendezvous);
                result = new ModelAndView("redirect:list.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(rendezvous, "rendezvous.commit.error");
            }
        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST,params = "delete")
    public ModelAndView edit(Rendezvous rendezvous){
        ModelAndView result;

        try{
            rendezvousService.delete(rendezvous);
            result = new ModelAndView("redirect:list.do");
        }catch (Throwable oops){
            result = createEditModelAndView(rendezvous,"rendezvous.delete.error");
        }

        return result;
    }


    // Ancillary methods ------------------------------------------------------

    protected ModelAndView createEditModelAndView(final Rendezvous rendezvous) {
        ModelAndView result;

        result = this.createEditModelAndView(rendezvous, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Rendezvous rendezvous, final String messageCode) {
        ModelAndView result;


        result = new ModelAndView("rendezvous/edit");
        result.addObject("rendezvous", rendezvous);



        result.addObject("message", messageCode);
        return result;
    }
}


