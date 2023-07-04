package app.askresume.oauth.constant

import app.askresume.oauth.exception.UnsupportedProviderException

enum class OAuthProvider {
    GOOGLE,
    LINKED_IN,
    ;

    companion object {

        /**
         * 케밥 케이스로 된 String을 넣으면 Enum으로 변환한 뒤 반환합니다.
         * @exception UnsupportedOAuthProviderException 매칭되는 Enum 값이 없으면 예외를 던집니다.
         */
        fun fromKebabCase(kebabCaseProvider: String): OAuthProvider {
            // kebab-case를 UPPER_SNAKE_CASE로 변환합니다.
            val upperSnakeCaseProvider = kebabCaseProvider.replace("-", "_").uppercase()

            // 해당 Provider가 목록에 없으면 예외를 던집니다.
            if (!has(upperSnakeCaseProvider))
                throw UnsupportedProviderException(providerName = upperSnakeCaseProvider)

            // 있으면 Enum으로 변환 후 반환합니다.
            return OAuthProvider.valueOf(upperSnakeCaseProvider)
        }

        /**
         * 입력된 값이 Provider 목록에 있는지 체크합니다.
         */
        fun has(value: String) = OAuthProvider.values().any { it.name == value }

    }
}