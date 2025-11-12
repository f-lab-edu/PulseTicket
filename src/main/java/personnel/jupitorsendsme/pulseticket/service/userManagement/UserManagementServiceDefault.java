package personnel.jupitorsendsme.pulseticket.service.userManagement;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import personnel.jupitorsendsme.pulseticket.interfaces.UserManagementService;

@Service
@Qualifier("default")
public class UserManagementServiceDefault implements UserManagementService {
    @Override
    public boolean doesUserExist(String username) {
        return false;
    }

    @Override
    public boolean registeringUser(String username, String password) {
        return false;
    }

    @Override
    public boolean isUserValid(String username, String password) {
        return false;
    }
}
