package personnel.jupitorsendsme.pulseticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import personnel.jupitorsendsme.pulseticket.service.reservationBooking.ReservationBookingA;

/**
 * ReservationBookingController 에서 사용될
 * 생성자 설정을 위한 클래스 (Factory method pattern)
 */
@Configuration
public class ReservationBookingConfig {

    @Bean("defaultReservationBookingA")
    public ReservationBookingA defaultReservationBookingA () {
        return new ReservationBookingA();
    }
}
