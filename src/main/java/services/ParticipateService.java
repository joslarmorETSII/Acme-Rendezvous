package services;

import domain.Participate;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.ParticipateRepository;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class ParticipateService {

    // Managed Repository -----------------------------------------------------
    @Autowired
    private ParticipateRepository participateRepository;

    // Supporting services ----------------------------------------------------
    @Autowired
    private UserService userService;

    @Autowired
    private RendezvousService rendezvousService;

    // Constructors -----------------------------------------------------------
    public ParticipateService(){
        super();
    }

    // Simple CRUD methods ----------------------------------------------------
    public Participate create(){
        Participate participate;
        User principal;

        principal = userService.findByPrincipal();
        participate = new Participate();
        participate.setAttendant(principal);

        return participate;
    }

    public Participate findOne(Integer participateId){
        return participateRepository.findOne(participateId);
    }

    public Collection<Participate> findAll(){
        return participateRepository.findAll();
    }

    public Participate save(Participate participate){
        Date currentDate = new Date();
        Assert.notNull(participate);
        checkByPrincipal(participate);
        //Assert.isTrue(currentDate.before(participate.getMoment()));
        participate.setMoment(currentDate);

        return participateRepository.save(participate);
    }

    public void delete(Participate participate){
        Assert.notNull(participate);
        checkByPrincipal(participate);
        Date currentDate=new Date();
        Assert.isTrue(participate.getRendezvous().getMoment().after(participate.getMoment()));
        if(participate.getAttendant().equals(participate.getRendezvous().getCreator())){
            rendezvousService.delete(participate.getRendezvous());
        }

        participateRepository.delete(participate);
    }

    // Other business methods -------------------------------------------------
    private void checkByPrincipal(Participate participate) {
        User principal = userService.findByPrincipal();
        Assert.isTrue(principal.equals(participate.getAttendant()));
    }

    public Participate participate(int attendantId,int rendezvousId){
        Participate participate =participateRepository.participate(attendantId,rendezvousId);
        return participate;
    }


}
