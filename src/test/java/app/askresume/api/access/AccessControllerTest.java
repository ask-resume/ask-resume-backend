package app.askresume.api.access;

import app.askresume.BaseSteps;
import app.askresume.api.access.dto.request.LoginRequest;
import app.askresume.api.access.dto.request.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("로그인,회원가입 테스트")
class AccessControllerTest extends BaseSteps {

    @Test
    @DisplayName("회원가입이 성공하면 상태코드는 200을 반환.")
    void ifYouSuccessfullySignUpReturnsStatusCode200() throws Exception {

        // given
        final SignUpRequest request = 회원가입요청_생성();

        // when
        MockHttpServletResponse response = 회원가입요청(request);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("이메일이 중복된 경우, 상태코드 400 반환.")
    void duplicateEmailStatusCode400() throws Exception {

        // given
        final SignUpRequest request = 회원가입요청_생성();

        // when
        회원가입요청(request);
        MockHttpServletResponse response = 회원가입요청(request);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("로그인 성공시, 상태코드 200 반환.")
    void successfulLoginReturnsStatusCode200() throws Exception {

        // given
        final SignUpRequest signUpRequest = 회원가입요청_생성();
        회원가입요청(signUpRequest);
        final LoginRequest loginRequest = 로그인요청_생성();

        // when
        MockHttpServletResponse response = 로그인요청(loginRequest);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("로그인 실패시, 상태코드 400 반환.")
    void loginFailsStatusCode400IsReturned() throws Exception {

        // given
        final SignUpRequest signUpRequest = 회원가입요청_생성();
        회원가입요청(signUpRequest);
        LoginRequest loginRequest = 로그인요청_생성();

        loginRequest = new LoginRequest(loginRequest.email(), "!wrongPassword1234");

        // when
        MockHttpServletResponse response = 로그인요청(loginRequest);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }



}