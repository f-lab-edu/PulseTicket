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
    public boolean isUserPresent(String userId) {
        return userRepo.existsByUserId(userId);
    }

    @Override
    public boolean registeringUser(String userId, String password) {
        if (this.isUserPresent(userId)) return false;

        User user = User.builder()
                .userId(userId)
                .passwordHash(passwordHashingService.hash(password))
                .build();

        userRepo.save(user);

        return true;
    }

    @Override
    public boolean isUserValid(String userId, String password) {
        return userRepo.findUserByUserId(userId)
                .map(user -> passwordHashingService.verify(password, user.getPasswordHash()))
                .orElse(false);
    }
}
