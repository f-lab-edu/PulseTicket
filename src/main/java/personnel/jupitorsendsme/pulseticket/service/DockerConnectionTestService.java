package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestRequest;
import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestResponse;
import personnel.jupitorsendsme.pulseticket.entity.DockerConnectionTestEntity;
import personnel.jupitorsendsme.pulseticket.repository.DockerConnectionTestRepository;

/**
 * Docker 연결 테스트 비즈니스 로직 서비스
 * PostgreSQL 데이터 저장 및 가공 로직
 */
@Service
@Transactional(readOnly = true)
public class DockerConnectionTestService {

    @Autowired
    private DockerConnectionTestRepository dockerConnectionTestRepository;

    /**
     * 테스트 데이터를 PostgreSQL에 저장하고 ', hello'를 추가한 결과를 반환
     * 저장과 가공 로직을 하나의 트랜잭션으로 처리
     *
     * @param request 저장할 테스트 데이터 요청
     * @return 저장된 데이터에 ', hello'가 추가된 응답
     */
    @Transactional
    public DockerConnectionTestResponse saveAndAppendHello(DockerConnectionTestRequest request) {
        // PostgreSQL에 데이터 저장
        DockerConnectionTestEntity savedEntity = saveTestData(request);

        // 저장된 content에 ', hello' 추가하여 응답 생성
        return createResponseWithHello(savedEntity);
    }

    /**
     * PostgreSQL에 테스트 데이터를 저장하는 로직
     * 데이터 저장 역할만 담당
     *
     * @param request 저장할 테스트 데이터 요청
     * @return 저장된 엔티티
     */
    private DockerConnectionTestEntity saveTestData(DockerConnectionTestRequest request) {
        DockerConnectionTestEntity entity = new DockerConnectionTestEntity(request.getName(), request.getContent());
        return dockerConnectionTestRepository.save(entity);
    }

    /**
     * 저장된 엔티티의 content에 ', hello'를 추가하여 응답 객체 생성
     * 데이터 가공 역할만 담당
     *
     * @param entity 저장된 엔티티
     * @return ', hello'가 추가된 응답 객체
     */
    private DockerConnectionTestResponse createResponseWithHello(DockerConnectionTestEntity entity) {
        String contentWithHello = entity.getContent() + ", hello";
        return new DockerConnectionTestResponse(entity.getName(), contentWithHello);
    }
}

