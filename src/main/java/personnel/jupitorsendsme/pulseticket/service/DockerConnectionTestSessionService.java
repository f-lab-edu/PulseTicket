package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestSessionData;

/**
 * Docker 연결 테스트 세션 관리 서비스
 * 세션에 데이터 저장, 조회, 초기화 기능 제공
 */
@Service
public class DockerConnectionTestSessionService {

    /**
     * 세션에 저장할 데이터의 키
     * 세션 속성에서 데이터를 저장하고 조회할 때 사용
     */
    private static final String SESSION_DATA_KEY = "sessionData";

    /**
     * 세션에 값을 저장하고 카운트를 증가시킴
     * 동일한 세션에서 여러 번 호출되면 카운트가 누적됨
     *
     * @param session HTTP 세션 객체
     * @param value 저장할 값
     * @return 저장된 세션 데이터
     */
    public DockerConnectionTestSessionData saveToSession(HttpSession session, String value) {
        // 기존 세션 데이터 조회 또는 새로 생성
        DockerConnectionTestSessionData sessionData = getOrCreateSessionData(session);

        // 값 업데이트 및 카운트 증가
        DockerConnectionTestSessionData updatedData = updateSessionData(sessionData, value);

        // 세션에 저장
        session.setAttribute(SESSION_DATA_KEY, updatedData);

        return updatedData;
    }

    /**
     * 현재 세션에 저장된 데이터를 조회
     * 세션에 데이터가 없으면 null 반환
     *
     * @param session HTTP 세션 객체
     * @return 저장된 세션 데이터 (없으면 null)
     */
    public DockerConnectionTestSessionData getFromSession(HttpSession session) {
        Object attribute = session.getAttribute(SESSION_DATA_KEY);
        return attribute instanceof DockerConnectionTestSessionData ? (DockerConnectionTestSessionData) attribute : null;
    }

    /**
     * 세션에 저장된 모든 데이터를 초기화
     * 세션을 무효화하여 Redis에서도 삭제됨
     *
     * @param session HTTP 세션 객체
     */
    public void clearSession(HttpSession session) {
        session.invalidate();
    }

    /**
     * 세션에서 기존 데이터를 조회하거나 새로 생성
     * 세션에 데이터가 있으면 기존 데이터 반환, 없으면 새로 생성
     *
     * @param session HTTP 세션 객체
     * @return 기존 세션 데이터 또는 새로 생성된 데이터
     */
    private DockerConnectionTestSessionData getOrCreateSessionData(HttpSession session) {
        DockerConnectionTestSessionData existingData = getFromSession(session);
        return existingData != null ? existingData : new DockerConnectionTestSessionData("", 0);
    }

    /**
     * 세션 데이터를 업데이트
     * 새로운 값을 저장하고 카운트를 증가시킴
     *
     * @param sessionData 기존 세션 데이터
     * @param value 저장할 새로운 값
     * @return 업데이트된 세션 데이터
     */
    private DockerConnectionTestSessionData updateSessionData(DockerConnectionTestSessionData sessionData, String value) {
        int newCount = sessionData.getCount() + 1;
        return new DockerConnectionTestSessionData(value, newCount);
    }
}

