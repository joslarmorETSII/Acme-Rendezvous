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
import java.util.Collection;

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
    public ModelAndView list() {
        ModelAndView result;
        Collection<Rendezvous> rendezvouses;

        User user = userService.findByPrincipal();
        rendezvouses = user.getRendezvouses();
        result = new ModelAndView("rendezvous/list");
        result.addObject("rendezvous", rendezvouses);
        result.addObject("user",user);
        result.addObject("requestUri","rendezvous/user/list.do");
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

   /* @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int noteId) {
        ModelAndView result;
        Note note;

        note = this.noteService.findOne(noteId);
        result = new ModelAndView("note/display");
        result.addObject("note", note);
        result.addObject("cancelURI", "note/auditor/list.do");

        return result;
    }

*/
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


       // result.addObject("cancelURI", "note/auditor/list.do");
        result.addObject("message", messageCode);
        return result;
    }
}


