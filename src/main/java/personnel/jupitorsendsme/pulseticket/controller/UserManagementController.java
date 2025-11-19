package personnel.jupitorsendsme.pulseticket.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.interfaces.UserManagementService;

/**
 * 사용자 관련 컨트롤러</br>
 * 조회/생성, 유효성 검사
 */
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserManagementController {
	private final UserManagementService userManagementService;

	@GetMapping("doesUserExist")
	Boolean doesUserExist(@ModelAttribute ReservationBookingRequest request) {
		return userManagementService.isUserPresent(request);
	}

	@PostMapping("registeringUser")
	Boolean registeringUser(@RequestBody ReservationBookingRequest request) {
		return userManagementService.registeringUser(request);
	}

	@GetMapping("isUserValid")
	Boolean isUserValid(@ModelAttribute ReservationBookingRequest request) {
		return userManagementService.isUserValid(request);
	}
}
