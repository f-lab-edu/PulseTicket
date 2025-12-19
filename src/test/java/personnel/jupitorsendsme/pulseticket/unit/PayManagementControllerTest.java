package personnel.jupitorsendsme.pulseticket.unit;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import personnel.jupitorsendsme.pulseticket.controller.PayManagementController;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.exception.user.UserNotFoundException;
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

	@Captor
	ArgumentCaptor<ReservationRequest> requestCaptor;

	private final BigDecimal validPaymentAmount = BigDecimal.valueOf(50000);

	@Test
	@DisplayName("적절한 값이 들어올 경우 controller 는 200 코드와 함께 true 를 리턴한다. service 에 값이 정확히 전달됬는지 확인한다.")
	public void pay_success() throws Exception {
		ReservationRequest validRequest = createValidReservationRequest();

		Mockito.doNothing().when(payManagementService).payReservation(any());

		mockMvc
			.perform(
				post("/api/reservation/pay")        // argument 에 validRequest 를 쓰면 안된다. 의미가 없다.
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(
						validRequest)))  // Controller 에 @RequestBody 를 썼더라도 content 로 값을 지정해주어야 하며, serialize 해야한다.
			.andExpect(status().isOk())
			.andExpect(content().string("true"));

		assertMethodParameterEquals(payManagementService, PayManagementService::payReservation, requestCaptor,
			validRequest);
	}

	@Test
	@DisplayName("결제를 하는데 없는 사용자 id 일 경우 UserNotFound Exception 을 ProblemDetail 형식으로 리턴")
	public void throw_UserNotFoundException_when_userIdIsWrong() throws Exception {

		final String notValidLoginId = "notValidLoginId";

		ReservationRequest requestWithNotValidLoginId = createValidReservationRequest();
		requestWithNotValidLoginId.setLoginId(notValidLoginId);

		doThrow(new UserNotFoundException(requestWithNotValidLoginId)).when(payManagementService)
			.payReservation(any());

		mockMvc
			.perform(
				post("/api/reservation/pay")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(requestWithNotValidLoginId)))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
			.andExpect(jsonPath("$.title").value("존재하지 않는 사용자"))
			.andExpect(jsonPath("$.detail").value(String.format("%s id 를 가진 사용자가 없음", notValidLoginId)));

		assertMethodParameterEquals(payManagementService, PayManagementService::payReservation, requestCaptor,
			requestWithNotValidLoginId);
	}

	public <T, E> void assertMethodParameterEquals(T mockClass, BiConsumer<T, E> targetMethod,
		ArgumentCaptor<E> captor, E targetObject) {
		targetMethod.accept(verify(mockClass), captor.capture());

		assertThat(captor.getValue())
			.usingRecursiveComparison()
			.isEqualTo(targetObject);
	}

	public ReservationRequest createValidReservationRequest() {
		String validLoginId = "validLoginId";
		String validPassword = "validPassword";
		long validEventId = 10L;
		Integer validSeatNumber = 20;
		Long validReservationId = 15L;

		return ReservationRequest
			.builder()
			.loginId(validLoginId)
			.password(validPassword)
			.eventId(validEventId)
			.seatNumber(validSeatNumber)
			.reservationId(validReservationId)
			.paymentAmount(validPaymentAmount)
			.build();
	}
}
