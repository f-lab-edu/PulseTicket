package personnel.jupitorsendsme.pulseticket.unit;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static personnel.jupitorsendsme.pulseticket.util.MockitoTestUtils.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import personnel.jupitorsendsme.pulseticket.controller.ReservationBookingController;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.service.ReservationBookingService;

@WebMvcTest(ReservationBookingController.class)
@ExtendWith(MockitoExtension.class)
public class ReservationBookingControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Captor
	ArgumentCaptor<ReservationRequest> requestCaptor;

	@Autowired
	ObjectMapper objectMapper;

	@MockitoBean
	ReservationBookingService reservationBookingService;

	@Test
	@DisplayName("예약에 적합한 값이 들어올 경우 '예약이 성공함' 메세지와 함께 예약 번호를 리턴한다")
	public void booking_success() throws Exception {
		Long validReservationId = 15L;
		ReservationBookingResponse bookingResult = ReservationBookingResponse
			.builder()
			.isSuccess(true)
			.reservationId(validReservationId)
			.build();

		ReservationRequest validRequest = createValidReservationRequest();

		Mockito.doReturn(bookingResult).when(reservationBookingService).book(any());

		mockMvc.perform(
				post("/api/reservation/booking")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(validRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.reservationId").value(validReservationId));

		assertMethodParameterEquals(reservationBookingService, ReservationBookingService::book,
			requestCaptor, validRequest);
	}

	public ReservationRequest createValidReservationRequest() {
		String validLoginId = "validLoginId";
		String validPassword = "validPassword";
		long validEventId = 10L;
		Integer validSeatNumber = 20;

		return ReservationRequest
			.builder()
			.loginId(validLoginId)
			.password(validPassword)
			.eventId(validEventId)
			.seatNumber(validSeatNumber)
			.build();
	}
}
