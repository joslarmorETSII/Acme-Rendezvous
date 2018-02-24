package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.RendezvousRepository;
import security.Authority;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class RendezvousService  {

    // Managed repository -----------------------------------------------------

    @Autowired
    private RendezvousRepository rendezvousRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserService userService;

    @Autowired
    private ParticipateService participateService;

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnnouncementService announcementService;
    // Constructors -----------------------------------------------------------

    public RendezvousService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Rendezvous create(){
        Rendezvous res=null;
        User creator=null;
        Collection<Comment> comments =new ArrayList<Comment>();
        Collection<Question> questions=new ArrayList<Question>();
        Collection<Announcement> announcements=new ArrayList<Announcement>();
        Collection<Participate> participated=new ArrayList<Participate>();
        Collection<Rendezvous> associated=new ArrayList<Rendezvous>();

        creator= userService.findByPrincipal();
        res=new Rendezvous();


        res.setCreator(creator);
        res.setAnnouncements(announcements);
        res.setAssociated(associated);
        res.setComments(comments);
        res.setParticipated(participated);
        res.setQuestions(questions);
        res.setFinalMode(false);
        res.setDeleted(false);
        creator.getRendezvouses().add(res);

        return res;
    }

    public Rendezvous save(Rendezvous rendezvous){
        Rendezvous res;
        Assert.isTrue(checkByPrincipal(rendezvous));
        Assert.notNull(rendezvous);
        if(rendezvous.getId()==0){
            Participate participate = participateService.create();
            participate.setMoment(new Date());
            participate.setAttendant(rendezvous.getCreator());

            res=rendezvousRepository.save(rendezvous);
            participate.setRendezvous(res);
            participateService.save(participate);
        }else{
           // Assert.isTrue(!rendezvous.getFinalMode());
            res=rendezvousRepository.save(rendezvous);
        }

        return res;
    }


    public Collection<Rendezvous> findAll() {

        Collection<Rendezvous> res = null;
        res = this.rendezvousRepository.findAll();
        return res;
    }

    public Rendezvous findOne(final int rendezvousId) {

        Rendezvous res = null;
        res = this.rendezvousRepository.findOne(rendezvousId);
        return res;
    }



    public Rendezvous delete(final Rendezvous rendezvous) {
        Rendezvous res=null;
        Assert.notNull(rendezvous);
        Assert.isTrue(this.checkByPrincipal(rendezvous) || checkByPrincipalAdmin(rendezvous));
        Assert.isTrue(!rendezvous.getFinalMode());
        rendezvous.setDeleted(true);
        return res= rendezvousRepository.save(rendezvous);
    }

    public void deleteAdmin(Rendezvous rendezvous){
        Assert.isTrue(checkByPrincipalAdmin(rendezvous));
        Assert.notNull(rendezvous);

        this.commentService.deleteComments(rendezvous);
        this.participateService.deleteParticipated(rendezvous);
        this.questionService.deleteQuestions(rendezvous);
        this.announcementService.deleteAnnouncements(rendezvous);
        rendezvousRepository.delete(rendezvous);

    }

    // Other business methods -------------------------------------------------

    public Rendezvous findOneToEdit(final int rendezvousId) {
        Rendezvous res = this.rendezvousRepository.findOne(rendezvousId);
        Assert.isTrue(checkByPrincipal(res));
        Assert.isTrue(!res.getFinalMode());
        return res;
    }

    public boolean checkByPrincipal(final Rendezvous rendezvous) {
        Boolean res = null;
        User principal = null;

        res = false;
        principal = this.userService.findByPrincipal();

        if (rendezvous.getCreator().equals(principal))
            res = true;

        return res;
    }

    public boolean checkByPrincipalAdmin(Rendezvous rendezvous){
        Boolean res= false;
        Administrator administrator = administratorService.findByPrincipal();
        Collection<Authority> authorities= administrator.getUserAccount().getAuthorities();
        String authority= authorities.toArray()[0].toString();
        res= authority.equals("ADMINISTRATOR");
        return res;

    }

    public Rendezvous findParticipate(int rendezvousId){
        User user= userService.findByPrincipal();
        Rendezvous rendezvous= rendezvousRepository.findOne(rendezvousId);
        Participate participate=participateService.participate(user.getId(),rendezvousId);
        Assert.notNull(participate);
        return  rendezvous;
    }

    public Collection<Rendezvous> userParticipate(Integer userId){
        return rendezvousRepository.userParticipate(userId);
    }

    public Collection<Rendezvous> rendezvousForAnonymous(){
        return rendezvousRepository.rendezvousForAnonymous();
    }

    public boolean mayor18(User attendant){
        Date fechaActual= new Date();
        Integer edad=  fechaActual.getYear()-attendant.getBirthday().getYear();
       return edad>=18;
    }

    public void associados(Rendezvous rendezvous) {

        rendezvousRepository.save(rendezvous);

    }


}
