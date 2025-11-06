package personnel.jupitorsendsme.pulseticket.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestRequest;
import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestResponse;
import personnel.jupitorsendsme.pulseticket.dto.DockerConnectionTestSessionData;
import personnel.jupitorsendsme.pulseticket.service.DockerConnectionTestService;
import personnel.jupitorsendsme.pulseticket.service.DockerConnectionTestSessionService;

/**
 * DockerConnectionTestController 테스트 클래스
 * 컨트롤러의 HTTP 엔드포인트 동작 검증
 */
@WebMvcTest(DockerConnectionTestController.class)
class DockerConnectionTestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DockerConnectionTestService dockerConnectionTestService;

    @MockitoBean
    private DockerConnectionTestSessionService dockerConnectionTestSessionService;

    /**
     * POST /api/test 엔드포인트 테스트
     * 테스트 데이터 저장 및 ', hello' 추가 응답 검증
     */
    @Test
    @DisplayName("POST /api/test - 테스트 데이터 저장 및 응답 검증")
    void testSaveTestData() throws Exception {
        // 요청 JSON 데이터 준비
        String requestJson = """
                {
                    "name": "testName",
                    "content": "testContent"
                }
                """;

        // Service 모킹 - 응답 설정
        DockerConnectionTestResponse expectedResponse = new DockerConnectionTestResponse("testName", "testContent, hello");
        when(dockerConnectionTestService.saveAndAppendHello(any(DockerConnectionTestRequest.class)))
                .thenReturn(expectedResponse);

        // HTTP 요청 및 응답 검증
        mockMvc.perform(post("/api/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("testName"))
                .andExpect(jsonPath("$.content").value("testContent, hello"));

        // Service 메서드 호출 검증
        verify(dockerConnectionTestService).saveAndAppendHello(any(DockerConnectionTestRequest.class));
    }

    /**
     * GET /api/test/session 엔드포인트 테스트
     * 세션 데이터 조회 및 세션 ID 반환 검증
     */
    @Test
    @DisplayName("GET /api/test/session - 세션 데이터 조회 검증")
    void testGetSession() throws Exception {
        // 세션 데이터 준비
        MockHttpSession session = new MockHttpSession();

        DockerConnectionTestSessionData sessionData = new DockerConnectionTestSessionData("testValue", 1);

        // Service 모킹 - 세션 데이터 반환 설정
        when(dockerConnectionTestSessionService.getFromSession(any()))
                .thenReturn(sessionData);

        // HTTP 요청 및 응답 검증
        mockMvc.perform(get("/api/test/session")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").exists())
                .andExpect(jsonPath("$.sessionId").isNotEmpty())
                .andExpect(jsonPath("$.data.value").value("testValue"))
                .andExpect(jsonPath("$.data.count").value(1));

        // Service 메서드 호출 검증
        verify(dockerConnectionTestSessionService).getFromSession(any());
    }

    /**
     * GET /api/test/session 엔드포인트 테스트 - 세션 데이터가 없는 경우
     * 세션에 데이터가 없을 때 null 반환 검증
     */
    @Test
    @DisplayName("GET /api/test/session - 세션 데이터가 없는 경우")
    void testGetSessionWhenNoData() throws Exception {
        // 세션 준비
        MockHttpSession session = new MockHttpSession();

        // Service 모킹 - null 반환 설정
        when(dockerConnectionTestSessionService.getFromSession(any()))
                .thenReturn(null);

        // HTTP 요청 및 응답 검증
        mockMvc.perform(get("/api/test/session")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").exists())
                .andExpect(jsonPath("$.sessionId").isNotEmpty())
                .andExpect(jsonPath("$.data").isEmpty());

        // Service 메서드 호출 검증
        verify(dockerConnectionTestSessionService).getFromSession(any());
    }

    /**
     * POST /api/test/session 엔드포인트 테스트
     * 세션에 값 저장 및 카운트 증가 검증
     */
    @Test
    @DisplayName("POST /api/test/session - 세션에 값 저장 검증")
    void testSaveToSession() throws Exception {
        // 세션 준비
        MockHttpSession session = new MockHttpSession();

        // 요청 JSON 데이터 준비
        String requestJson = """
                {
                    "value": "testValue"
                }
                """;

        // Service 모킹 - 저장된 세션 데이터 반환 설정
        DockerConnectionTestSessionData savedData = new DockerConnectionTestSessionData("testValue", 1);
        when(dockerConnectionTestSessionService.saveToSession(any(), any(String.class)))
                .thenReturn(savedData);

        // HTTP 요청 및 응답 검증
        mockMvc.perform(post("/api/test/session")
                .session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sessionId").exists())
                .andExpect(jsonPath("$.sessionId").isNotEmpty())
                .andExpect(jsonPath("$.data.value").value("testValue"))
                .andExpect(jsonPath("$.data.count").value(1));

        // Service 메서드 호출 검증
        verify(dockerConnectionTestSessionService).saveToSession(any(), any(String.class));
    }

    /**
     * DELETE /api/test/session/clear 엔드포인트 테스트
     * 세션 초기화 검증
     */
    @Test
    @DisplayName("DELETE /api/test/session/clear - 세션 초기화 검증")
    void testClearSession() throws Exception {
        // 세션 준비
        MockHttpSession session = new MockHttpSession();

        // HTTP 요청 및 응답 검증
        mockMvc.perform(delete("/api/test/session/clear")
                .session(session))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        // Service 메서드 호출 검증
        verify(dockerConnectionTestSessionService).clearSession(any());
    }

}

