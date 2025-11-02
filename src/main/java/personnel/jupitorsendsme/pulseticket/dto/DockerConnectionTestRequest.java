package personnel.jupitorsendsme.pulseticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Docker 연결 테스트 요청 DTO
 * HTTP 요청에서 전달되는 테스트 데이터를 담는 객체
 */
@Getter
@NoArgsConstructor
public class DockerConnectionTestRequest {

    /**
     * 테스트 데이터의 이름
     */
    private String name;

    /**
     * 테스트 데이터의 내용
     */
    private String content;
}

