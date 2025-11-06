package personnel.jupitorsendsme.pulseticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestRequest;
import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestResponse;
import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestSessionRequest;
import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestSessionResponse;
import personnel.jupitorsendsme.pulseticket.service.DockerConnectionTestService;
import personnel.jupitorsendsme.pulseticket.service.DockerConnectionTestSessionService;

/**
 * Docker 연결 테스트 요청을 처리하는 컨트롤러
 * HTTP 요청/응답 처리
 */
@RestController
@RequestMapping("/api/test")
public class DockerConnectionTestController {

    @Autowired
    private DockerConnectionTestService dockerConnectionTestService;

    @Autowired
    private DockerConnectionTestSessionService dockerConnectionTestSessionService;

    /**
     * 테스트 데이터를 저장하고 ', hello'가 추가된 결과를 반환하는 엔드포인트
     * PostgreSQL에 데이터를 저장하고 가공된 결과를 반환
     *
     * @param request 저장할 테스트 데이터 요청
     * @return 저장된 데이터에 ', hello'가 추가된 응답
     */
    @PostMapping
    public ResponseEntity<DockerConnectionTestResponse> saveTestData(@RequestBody DockerConnectionTestRequest request) {
        DockerConnectionTestResponse response = dockerConnectionTestService.saveAndAppendHello(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 현재 세션 상태를 조회하는 엔드포인트
     * Redis에 저장된 세션 데이터와 세션 ID를 반환
     *
     * @param session HTTP 세션 객체
     * @return 세션 ID와 저장된 데이터
     */
    @GetMapping("/session")
    public ResponseEntity<DockerConnectionTestSessionResponse> getSession(HttpSession session) {
        // 세션에서 데이터 조회
        var sessionData = dockerConnectionTestSessionService.getFromSession(session);

        // 세션 ID 추출
        String sessionId = session.getId();

        // 응답 생성
        DockerConnectionTestSessionResponse response = new DockerConnectionTestSessionResponse(sessionId, sessionData);
        return ResponseEntity.ok(response);
    }

    /**
     * 세션에 값을 저장하는 엔드포인트
     * Redis에 세션 데이터를 저장하고 카운트를 증가시킴
     *
     * @param session HTTP 세션 객체
     * @param request 저장할 값이 담긴 요청
     * @return 저장된 세션 데이터와 세션 ID
     */
    @PostMapping("/session")
    public ResponseEntity<DockerConnectionTestSessionResponse> saveToSession(
            HttpSession session,
            @RequestBody DockerConnectionTestSessionRequest request) {
        // 세션에 값 저장
        var savedData = dockerConnectionTestSessionService.saveToSession(session, request.getValue());

        // 세션 ID 추출
        String sessionId = session.getId();

        // 응답 생성
        DockerConnectionTestSessionResponse response = new DockerConnectionTestSessionResponse(sessionId, savedData);
        return ResponseEntity.ok(response);
    }

    /**
     * 세션을 초기화하는 엔드포인트
     * Redis에서 세션 데이터를 삭제
     *
     * @param session HTTP 세션 객체
     * @return 성공 응답
     */
    @DeleteMapping("/session/clear")
    public ResponseEntity<Void> clearSession(HttpSession session) {
        // 세션 초기화
        dockerConnectionTestSessionService.clearSession(session);
        return ResponseEntity.noContent().build();
    }
}

