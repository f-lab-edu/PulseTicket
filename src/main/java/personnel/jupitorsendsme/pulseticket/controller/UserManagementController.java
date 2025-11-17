package personnel.jupitorsendsme.pulseticket.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import personnel.jupitorsendsme.pulseticket.dto.UserManagementRequest;
import personnel.jupitorsendsme.pulseticket.interfaces.UserManagementService;

/**
 * 사용자 관련 컨트롤러</br>
 * 조회/생성, 유효성 검사
 */
@RestController
@RequestMapping("api/user")
public class UserManagementController {
    private final UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @GetMapping("doesUserExist")
    ResponseEntity<Boolean> doesUserExist (@RequestParam String username) {
        Boolean response = userManagementService.doesUserExist(username);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("registeringUser")
    ResponseEntity<Boolean> registeringUser (@RequestBody UserManagementRequest request) {
        Boolean response = userManagementService.registeringUser(request.getUserId(), request.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("isUserValid")
    ResponseEntity<Boolean> isUserValid (@RequestParam String username, @RequestParam String password) {
        Boolean response = userManagementService.isUserValid(username, password);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
