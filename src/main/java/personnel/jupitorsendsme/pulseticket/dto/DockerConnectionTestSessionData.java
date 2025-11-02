package personnel.jupitorsendsme.pulseticket.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Docker 연결 테스트 세션 데이터 DTO
 * 세션 상태를 저장하고 조회하기 위한 간단한 데이터 모델
 * Redis에 저장하기 위해 Serializable 구현 필요
 */
@Getter
@NoArgsConstructor
public class DockerConnectionTestSessionData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 세션에 저장된 값
     */
    private String value;

    /**
     * 세션에 값이 저장된 횟수
     */
    private Integer count;

    /**
     * DockerConnectionTestSessionData 생성자
     *
     * @param value 세션에 저장할 값
     * @param count 세션에 값이 저장된 횟수
     */
    public DockerConnectionTestSessionData(String value, Integer count) {
        this.value = value;
        this.count = count;
    }
}

