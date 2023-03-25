package app.askresume.api.member.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ModifyInfoRequest 단위테스트")
class ModifyInfoRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @DisplayName("사용자이름, 프로필이 올바르게 들어간 경우.")
    @Test
    void ifBothValuesAreEnteredCorrectly() {
        final String username = "홍길동";
        final String profile = "https://domain.com/img_110x110.jpg";
        ModifyInfoRequest request = new ModifyInfoRequest(username, profile);

        Set<ConstraintViolation<ModifyInfoRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @DisplayName("프로필이 NULL이면 정규식 미작동.")
    @Test
    void ifProfileIsNullRegularExpressionDoesNotWork() {
        final String username = "홍길동";
        final String profile = null;
        ModifyInfoRequest request = new ModifyInfoRequest(username, profile);

        Set<ConstraintViolation<ModifyInfoRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @DisplayName("프로필이 URL 형태가 아닌 경우.")
    @Test
    void ifTheProfileIsNotInTheFormOfUrl() {
        final String username = "홍길동";
        final String profile = "no_url";
        ModifyInfoRequest request = new ModifyInfoRequest(username, profile);

        Set<ConstraintViolation<ModifyInfoRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());

        ConstraintViolation<ModifyInfoRequest> violation = violations.iterator().next();
        assertEquals("올바른 URL 형식이 아닙니다.", violation.getMessage());
    }

}