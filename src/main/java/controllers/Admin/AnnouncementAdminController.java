package controllers.Admin;

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

@Controller
@RequestMapping("/announcement/admin")
public class AnnouncementAdminController extends AbstractController{


    // Services --------------------------------------------

    @Autowired
    private AnnouncementService announcementService;

    // Constructor -----------------------------------------
    public AnnouncementAdminController() {
        super();
    }


    // Delete

    @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(final Announcement announcement) {
        ModelAndView res = null;

        try {
            this.announcementService.delete(announcement);
            res = new ModelAndView("redirect:/announcement/list.do");
        } catch (final Throwable t) {
            res = this.createEditModelAndView(announcement, "announcement.delete.error");
        }
        return res;
    }

    // Ancillary methods

    private ModelAndView createEditModelAndView(final Announcement announcement) {

        // return this.createEditModelAndView(announcement, null);
        return this.createEditModelAndView(announcement, null);
    }

    private ModelAndView createEditModelAndView(final Announcement announcement, final String message) {

        ModelAndView res = new ModelAndView("announcement/edit");
        res.addObject("announcement", announcement);
        res.addObject("message", message);
        res.addObject("actionUri","announcement/admin/edit.do");

        return res;

    }
}
