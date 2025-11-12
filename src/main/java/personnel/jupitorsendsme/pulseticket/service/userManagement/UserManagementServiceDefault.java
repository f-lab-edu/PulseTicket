package personnel.jupitorsendsme.pulseticket.service.userManagement;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.interfaces.PasswordHashingService;
import personnel.jupitorsendsme.pulseticket.interfaces.UserManagementService;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;

@Service
@Qualifier("default")
public class UserManagementServiceDefault implements UserManagementService {

    final UserRepository userRepo;
    final PasswordHashingService passwordHashingService;

    public UserManagementServiceDefault(UserRepository userRepo,
                                        @Qualifier("Argon2id") PasswordHashingService passwordHashingService) {
        this.userRepo = userRepo;
        this.passwordHashingService = passwordHashingService;
    }

    @Override
    public boolean doesUserExist(String username) {
        return userRepo.existsByUsername(username);
    }

    @Override
    public boolean registeringUser(String username, String password) {
        if (this.doesUserExist(username)) return false;

        User user = User.builder()
                .username(username)
                .passwordHash(passwordHashingService.hash(password))
                .build();

        userRepo.save(user);

        return true;
    }

    @Override
    public boolean isUserValid(String username, String password) {
        return userRepo.exitsByUsernameAndPasswordHash(username, passwordHashingService.hash(password));
    }
}
