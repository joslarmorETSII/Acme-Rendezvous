package controllers.AnnouncementUserController;

import controllers.AbstractController;
import domain.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AnnouncementService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/announcement/user")
public class AnnouncementUserController extends AbstractController {

    // Services --------------------------------------------

    @Autowired
    private AnnouncementService announcementService;

    // Constructor -----------------------------------------
    public AnnouncementUserController() {
        super();
    }


    // Create --------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {

        final Announcement announcement = this.announcementService.create();

        final ModelAndView res = this.createEditModelAndView(announcement);

        return res;
    }

    // Edit --------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam(required = true) final Integer announcementId) {

        final Announcement announcement = this.announcementService.findOne(announcementId);
        Assert.notNull(announcement);

        final ModelAndView res = this.createEditModelAndView(announcement);

        return res;
    }

    //Save --------------------------------------------

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final Announcement announcement, final BindingResult binding) {

        ModelAndView res = null;

        if (binding.hasErrors())
            res = this.createEditModelAndView(announcement);
        else
            try {
                this.announcementService.save(announcement);
                res = new ModelAndView("redirect:/announcement/list.do");
            } catch (final Throwable t) {
                res = this.createEditModelAndView(announcement, "announcement.commit.error");
            }
        return res;
    }


    // Listing --------------------------------------------

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {

        final Collection<Announcement> announcements;
        announcements = this.announcementService.findAll();

        final ModelAndView res = new ModelAndView("announcement/list");
        res.addObject("announcements", announcements);
        res.addObject("requestURI", "announcement/list.do");

        return res;
    }
    // Ancillary methods

    private ModelAndView createEditModelAndView(final Announcement announcement) {

        // return this.createEditModelAndView(announcement, null);
        return this.createEditModelAndView(announcement, null);
    }

    private ModelAndView createEditModelAndView(final Announcement announcement, final String message) {

        final ModelAndView res = new ModelAndView("announcement/edit");
        res.addObject("announcement", announcement);
        res.addObject("message", message);
        res.addObject("actionUri", "announcement/user/create.do");
        res.addObject("cancelUri","announcement/user/list.do");

        return res;

    }
}
