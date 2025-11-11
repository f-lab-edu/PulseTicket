package personnel.jupitorsendsme.pulseticket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import personnel.jupitorsendsme.pulseticket.service.reservationQuery.ReservationQueryServiceDefault;

/**
 * ReservationQueryController 에서 사용될
 * 생성자 설정을 위한 클래스 (Factory method Pattern)
 */
@Configuration
public class ReservationQueryConfig {

    @Bean("defaultReservationQueryA")
    public ReservationQueryServiceDefault defaultReservationBookingA () {
        return new ReservationQueryServiceDefault();
    }
}
