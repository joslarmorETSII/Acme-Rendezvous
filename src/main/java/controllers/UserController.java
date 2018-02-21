package controllers;


import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{

    // Services --------------------------------------------

    @Autowired
    private UserService userService;

    // Constructor -----------------------------------------
    public UserController() {
        super();
    }

    // Edition  ----------------------------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit() {

        ModelAndView result;

        final User user = this.userService.findByPrincipal();
        result = this.createEditModelAndView(user);

        return result;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final User user, final BindingResult binding) {

        ModelAndView result;

        if (binding.hasErrors())
            result = this.createEditModelAndView(user);
        else
            try {
                this.userService.save(user);
                result = new ModelAndView("redirect:/welcome/index.do");
            } catch (final Throwable oops) {
                result = this.createEditModelAndView(user, "user.commit.error");
            }
        return result;
    }

    // Listing -------------------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView result;

        Collection<User> users;
        users = userService.findAll();

        result = new ModelAndView("user/list");
        result.addObject("users", users);
        result.addObject("requestURI", "user/list.do");
        result.addObject("users",users);
        return result;

    }

    // Ancillary methods ------------------------------------------------------------

    protected ModelAndView createEditModelAndView(final User user) {

        ModelAndView result;

        result = this.createEditModelAndView(user, null);

        return result;
    }

    protected ModelAndView createEditModelAndView(final User user, final String message) {

        Assert.notNull(user);

        ModelAndView result;

        result = new ModelAndView("user/edit");
        result.addObject("user", user);
        result.addObject("message", message);

        return result;
    }

}
