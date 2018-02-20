package services;

import domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.AdminRepository;
import security.LoginService;
import security.UserAccount;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class AdminService {

    // Managed repository -----------------------------------------------------

    @Autowired
    private AdminRepository adminRepository;

    // Supporting services ----------------------------------------------------

    @Autowired
    private UserAccountService userAccountService;

    // Constructors -----------------------------------------------------------

    public AdminService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    public Admin create() {

        Admin result;
        result = new Admin();
        final UserAccount userAccount = this.userAccountService.create("ADMIN");
        result.setUserAccount(userAccount);
        return result;
    }

    public Admin findOne(final int adminId) {

        Admin result;
        result = this.adminRepository.findOne(adminId);
        return result;
    }

    public Collection<Admin> findAll() {

        Collection<Admin> result;
        result = this.adminRepository.findAll();
        return result;
    }

    public Admin save(final Admin admin) {

        Assert.notNull(admin);

        Admin result;

        result = this.adminRepository.save(admin);

        return result;
    }

    // Other business methods -------------------------------------------------

    public Admin findByPrincipal() {

        Admin result;
        final UserAccount userAccount = LoginService.getPrincipal();
        result = this.findByUserAccountId(userAccount.getId());
        return result;
    }

    public Admin findByUserAccountId(final int userAccountId) {

        Admin result;
        result = this.adminRepository.findByUserAccountId(userAccountId);
        return result;
    }



}
