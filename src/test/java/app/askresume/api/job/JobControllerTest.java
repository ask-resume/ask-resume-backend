package app.askresume.api.job;

import app.askresume.BaseSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


class JobControllerTest extends BaseSteps {

    private String accessToken;

    @BeforeEach
    public void login() throws Exception {
        accessToken = 회원가입_토큰();
    }

    @Test
    @DisplayName("직업리스트를 정상적인, accept_language으로 조회하면 200을 반환")
    void ifYouLookUpJobListNormally200IsReturned() throws Exception {

        // given & when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/jobs")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(HttpHeaders.AUTHORIZATION, accessToken)
                                .header(HttpHeaders.ACCEPT_LANGUAGE, "en-US")
                )
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("직업리스트를 올바르지 않는, accept_language으로 조회하면 200을 반환")
    void ifYouLookUpJobListIncorrectly200IsReturned() throws Exception {

        // given & when
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/jobs")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .header(HttpHeaders.AUTHORIZATION, accessToken)
                                .header(HttpHeaders.ACCEPT_LANGUAGE, "strange")
                )
                .andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}