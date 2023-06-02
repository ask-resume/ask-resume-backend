package app.askresume.domain.locale.constant


enum class LocaleType(
    val value: String
) {

    KO("Korean"),
    EN("English"),
    ;

    fun value() = value

    companion object {
        fun from(type: String): LocaleType {
            return LocaleType.valueOf(type.uppercase())
        }

        fun isLocaleType(type: String): Boolean {
            return LocaleType.values().any { it.name == type.uppercase() }
        }
    }
}

