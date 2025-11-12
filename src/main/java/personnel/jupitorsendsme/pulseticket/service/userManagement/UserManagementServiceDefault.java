package personnel.jupitorsendsme.pulseticket.service.userManagement;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import personnel.jupitorsendsme.pulseticket.interfaces.UserManagementService;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;

@Service
@Qualifier("default")
public class UserManagementServiceDefault implements UserManagementService {

    final UserRepository userRepo;

    public UserManagementServiceDefault(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean doesUserExist(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean registeringUser(String username, String password) {
        if (!this.doesUserExist(username)) return false;

        return false;
    }

    @Override
    public boolean isUserValid(String username, String password) {
        return false;
    }
}
