package personnel.jupitorsendsme.pulseticket.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.service.PayManagementService;

@RestController
@RequestMapping("api/reservation/pay")
@RequiredArgsConstructor
public class PayManagementController {

	private final PayManagementService payManagementService;

	@PostMapping
	public Boolean pay(@RequestBody ReservationRequest request) {
		payManagementService.payReservation(request);

		return true;
	}
}
