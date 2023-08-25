package app.askresume.domain.submit.constant

enum class ServiceType(
    val model: String,
    val maxTokens: Int,
) {

    INTERVIEW_MAKER(
        model = "gpt-3.5-turbo",
        maxTokens = 3000,
    ),
    INTERVIEW_MAKER_PDF(
        model = "gpt-3.5-turbo-16k",
        maxTokens = 10000,
    ),
    ;


    companion object {
        fun from(type: String): ServiceType {
            return ServiceType.valueOf(type.uppercase())
        }

    }
}

