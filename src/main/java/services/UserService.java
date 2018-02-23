package services;

import domain.Comment;
import domain.Participate;
import domain.Rendezvous;
import domain.User;
import forms.UserForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class UserService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private UserRepository userRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private ActorService actorService;

    @Autowired
    private UserAccountService userAccountService;

    // Constructors -----------------------------------------------------------

    public UserService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public User create() {
        User result;

        result = new User();

        result.setBirthday(result.getBirthday());
        result.setComments(new ArrayList<Comment>());
        result.setParticipates(new ArrayList<Participate>());
        result.setRendezvouses(new ArrayList<Rendezvous>());
        result.setUserAccount(this.userAccountService.create("USER"));

        return result;
    }

    public User findOne(final int userId) {

        User result;
        result = this.userRepository.findOne(userId);
        return result;
    }

    public Collection<User> findAll() {

        Collection<User> result;
        result = this.userRepository.findAll();
        return result;
    }

    public User save(final User user) {

        Assert.notNull(user);

        User result;

        if (user.getId() == 0) {
            result = this.userRepository.save(user);
        } else
            result = this.userRepository.save(user);

        return result;
    }

    // Other business methods

    public User findByPrincipal() {

        User result;
        final UserAccount userAccount = LoginService.getPrincipal();
        result = this.findByUserAccountId(userAccount.getId());
        return result;
    }



    public User findByUserAccountId(final int userAccountId) {

        User result;
        result = this.userRepository.findByUserAccountId(userAccountId);
        return result;
    }

    public User reconstruct(final UserForm userForm, final BindingResult binding) {

        User result;

        result = this.create();
        result.getUserAccount().setUsername(userForm.getUsername());
        result.setName(userForm.getName());
        result.setSurname(userForm.getSurname());
        result.setPhone(userForm.getPhone());
        result.setEmail(userForm.getEmail());
        result.setPostalAdresses(userForm.getPostalAdresses());
        result.setBirthday(userForm.getBirthday());
        result.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(userForm.getPassword(), null));

        this.comprobarContrasena(userForm.getPassword(), userForm.getRepeatPassword(), binding);

        return result;
    }

    private boolean comprobarContrasena(final String password, final String passwordRepeat, final BindingResult binding) {
        FieldError error;
        String[] codigos;
        boolean result;

        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(passwordRepeat))
            result = password.equals(passwordRepeat);
        else
            result = false;

        if (!result) {
            codigos = new String[1];
            codigos[0] = "user.password.mismatch";
            error = new FieldError("userForm", "password", password, false, codigos, null, "");
            binding.addError(error);
        }

        return result;
    }



}
