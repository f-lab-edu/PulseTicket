package personnel.jupitorsendsme.pulseticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import personnel.jupitorsendsme.pulseticket.service.reservationBooking.ReservationBookingServiceDefault;

/**
 * ReservationBookingController 에서 사용될
 * 생성자 설정을 위한 클래스 (Factory method pattern)
 */
@Configuration
public class ReservationBookingConfig {

    @Bean
    @Scope("singleton")
    public ReservationBookingServiceDefault reservationBookingServiceDefault() {
        return new ReservationBookingServiceDefault();
    }
}
