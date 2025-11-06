package personnel.jupitorsendsme.pulseticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Docker 연결 테스트 세션 요청 DTO
 * 세션에 저장할 값을 담는 요청 객체
 */
@Getter
@NoArgsConstructor
public class DockerConnectionTestSessionRequest {

    /**
     * 세션에 저장할 값
     */
    private String value;
}

