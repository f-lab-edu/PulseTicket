package personnel.jupitorsendsme.pulseticket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Docker 연결 테스트용 엔티티
 * PostgreSQL과의 상호작용을 테스트하기 위한 간단한 데이터 모델
 */
@Entity
@Table(name = "test")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DockerConnectionTestEntity {

    /**
     * 테스트 데이터의 고유 식별자
     * 자동 증가되는 기본 키
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 테스트 데이터의 이름
     * NULL을 허용하지 않음
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * 테스트 데이터의 내용
     * NULL을 허용하지 않음
     */
    @Column(nullable = false, length = 1000)
    private String content;

    /**
     * DockerConnectionTestEntity 생성자
     * 엔티티의 불변성을 보장하기 위해 생성자로만 초기화 가능
     *
     * @param name 테스트 데이터의 이름
     * @param content 테스트 데이터의 내용
     */
    public DockerConnectionTestEntity(String name, String content) {
        this.name = name;
        this.content = content;
    }
}

