package app.askresume.oauth.constant

enum class OAuthProvider {
    GOOGLE,
    LINKED_IN,
    ;

    companion object {

        /**
         * 케밥 케이스로 된 String을 넣으면 Enum으로 변환한 뒤 반환합니다.
         * @exception UnsupportedOAuthProviderException 매칭되는 Enum 값이 없으면 예외를 던집니다.
         */
        fun fromKebabCase(kebabCase: String): OAuthProvider {
            // @PathVariable의 kebab-case를 상수 컨벤션인 UPPER_SNAKE_CASE로 변환합니다.
            val upperSnakeCase = kebabCase.replace("-", "_").uppercase()

            // UPPER_SNAKE_CASE String이 Enum 값 목록에 없으면 예외를 던집니다.
            if (!has(upperSnakeCase))
                throw Exception("지원하지 않는 OAuth Provider입니다. : $upperSnakeCase") // TODO

            // 있으면 Enum으로 변환 후 반환합니다.
            return OAuthProvider.valueOf(upperSnakeCase)
        }

        /**
         * 입력된 값이 Enum 목록에 있으면 true, 없으면 false를 반환합니다.
         */
        fun has(value: String) = OAuthProvider.values().any { it.name == value }

    }
}