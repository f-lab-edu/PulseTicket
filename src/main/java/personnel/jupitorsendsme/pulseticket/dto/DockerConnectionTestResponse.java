package personnel.jupitorsendsme.pulseticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Docker 연결 테스트 응답 DTO
 * PostgreSQL에 저장된 데이터에 ', hello'를 추가한 결과를 담는 객체
 */
@Getter
@NoArgsConstructor
public class DockerConnectionTestResponse {

    /**
     * 테스트 데이터의 이름
     */
    private String name;

    /**
     * 원본 내용에 ', hello'가 추가된 최종 내용
     */
    private String content;

    /**
     * DockerConnectionTestResponse 생성자
     *
     * @param name 테스트 데이터의 이름
     * @param content 원본 내용에 ', hello'가 추가된 최종 내용
     */
    public DockerConnectionTestResponse(String name, String content) {
        this.name = name;
        this.content = content;
    }
}

