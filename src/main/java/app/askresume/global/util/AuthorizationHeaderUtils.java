package app.askresume.global.util;

import app.askresume.global.jwt.constant.GrantType;
import app.askresume.global.error.ErrorCode;
import app.askresume.global.error.exception.AuthenticationException;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * Header에서 Authorization 관련 체크 Util
 *
 * @author igor
 */
@UtilityClass
public class AuthorizationHeaderUtils {

    public static void validateAuthorization(String authorizationHeader) {

        // 1. authorizationHeader 필수 체크
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. authorizationHeader Bearer 체크
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }

}
