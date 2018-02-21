package services;

import domain.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AdministratorService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AdministratorRepository administratorRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;

    // Constructors -----------------------------------------------------------

    public AdministratorService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Administrator create() {

        Administrator result;
        result = new Administrator();
        final UserAccount userAccount = this.userAccountService.create("ADMIN");
        result.setUserAccount(userAccount);
        return result;
    }

    public Administrator findOne(final int administratorId) {

        Administrator result;
        result = this.administratorRepository.findOne(administratorId);
        return result;
    }

    public Collection<Administrator> findAll() {

        Collection<Administrator> result;
        result = this.administratorRepository.findAll();
        return result;
    }

    public Administrator save(final Administrator administrator) {

        Assert.notNull(administrator);

        Administrator result;

        result = this.administratorRepository.save(administrator);

        return result;
    }

    // Other business methods -------------------------------------------------

    public Administrator findByPrincipal() {

        Administrator result;
        final UserAccount userAccount = LoginService.getPrincipal();
        result = this.findByUserAccountId(userAccount.getId());
        return result;
    }

    public Administrator findByUserAccountId(final int userAccountId) {

        Administrator result;
        result = this.administratorRepository.findByUserAccountId(userAccountId);
        return result;
    }



}
