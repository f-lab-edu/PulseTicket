package personnel.jupitorsendsme.pulseticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 예약 조회 서비스에 대한 응답 객체
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationQueryResponse {

    /**
     * 예약 정보 리스트
     */
    List<ReservationInformation> reservationInformationList;
}
