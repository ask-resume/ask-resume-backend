package app.askresume;

import app.askresume.api.access.dto.request.LoginRequest;
import app.askresume.api.access.dto.request.SignUpRequest;
import app.askresume.api.access.dto.response.LoginResponse;
import app.askresume.global.model.ApiResult;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseSteps extends ApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final String EMAIL = "member@domain.co.kr";
    private final String PASSWORD = "!Password1234";
    private final String LOCALE = "EN";

    public MockHttpServletResponse 회원가입요청(SignUpRequest signUpRequest) throws Exception {
        return mockMvc.perform(
                        post("/api/sign-up")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(signUpRequest))
                )
                .andDo(print())
                .andReturn().getResponse();
    }

    public SignUpRequest 회원가입요청_생성() {
        final String profile = "https://domain.com/img_110x110.jpg";
        final String username = "회원";

        return new SignUpRequest(EMAIL, PASSWORD, PASSWORD, username, profile, LOCALE);
    }

    public MockHttpServletResponse 로그인요청(LoginRequest loginRequest) throws Exception {
        return mockMvc.perform(
                        post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(loginRequest))
                )
                .andDo(print())
                .andReturn().getResponse();
    }

    public LoginRequest 로그인요청_생성() {
        return new LoginRequest(EMAIL, PASSWORD);
    }

    public String 로그인_토큰(LoginRequest loginRequest) throws Exception {
        MockHttpServletResponse response = 로그인요청(loginRequest);
        return getResponseObject(response, LoginResponse.class).accessToken();
    }


    protected <T> T getResponseObject(MockHttpServletResponse response, Class<T> type) throws Exception {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ApiResult.class, type);
        ApiResult<T> result = objectMapper.readValue(response.getContentAsString(), javaType);
        return result.getData();
    }
}
