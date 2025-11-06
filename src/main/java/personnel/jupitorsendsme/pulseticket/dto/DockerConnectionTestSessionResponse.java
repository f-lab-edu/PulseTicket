package personnel.jupitorsendsme.pulseticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Docker 연결 테스트 세션 응답 DTO
 * 세션 ID와 저장된 데이터를 담는 응답 객체
 */
@Getter
@NoArgsConstructor
public class DockerConnectionTestSessionResponse {

    /**
     * 현재 세션 ID
     */
    private String sessionId;

    /**
     * 세션에 저장된 데이터
     */
    private DockerConnectionTestSessionData data;

    /**
     * DockerConnectionTestSessionResponse 생성자
     *
     * @param sessionId 현재 세션 ID
     * @param data 세션에 저장된 데이터
     */
    public DockerConnectionTestSessionResponse(String sessionId, DockerConnectionTestSessionData data) {
        this.sessionId = sessionId;
        this.data = data;
    }
}

