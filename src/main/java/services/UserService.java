package services;

import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.UserRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
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

        result.setBirthday(new Date(System.currentTimeMillis() - 1000));
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
            user.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(user.getUserAccount().getPassword(),null));
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



}
