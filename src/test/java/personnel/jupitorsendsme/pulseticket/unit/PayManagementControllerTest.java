package personnel.jupitorsendsme.pulseticket.unit;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import personnel.jupitorsendsme.pulseticket.controller.PayManagementController;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.service.PayManagementService;

@WebMvcTest(PayManagementController.class)
@ExtendWith(MockitoExtension.class)
public class PayManagementControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	PayManagementService payManagementService;

	@Test
	@DisplayName("적절한 값이 들어올 경우 controller 는 200 코드와 함께 true 를 리턴한다. service 에 값이 정확히 전달됬는지 확인한다.")
	public void pay_success() throws Exception {
		ReservationRequest validRequest = createValidReservationRequest();

		Mockito.doNothing().when(payManagementService).payReservation(validRequest);

		mockMvc
			.perform(
				post("/api/reservation/pay")        // argument 에 validRequest 를 쓰면 안된다. 의미가 없다.
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(
						validRequest)))  // Controller 에 @RequestBody 를 썼더라도 content 로 값을 지정해주어야 하며, serialize 해야한다.
			.andExpect(status().isOk())
			.andExpect(content().string("true"));

		verify(payManagementService).payReservation(
			refEq(validRequest));    // refEq 를 안쓰면 주소값이 달라 테스트에 실패한다. 값을 검증하려면 refEq 를 써야한다.
	}

	public ReservationRequest createValidReservationRequest() {
		return ReservationRequest
			.builder()
			.loginId("validLoginId")
			.password("validPassword")
			.eventId(10L)
			.seatNumber(20)
			.reservationId(5L)
			.paymentAmount(BigDecimal.valueOf(50000))
			.build();
	}
}
