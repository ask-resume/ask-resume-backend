package app.askresume.domain.submit.constant

enum class ServiceType {

    INTERVIEW_MAKER,
    INTERVIEW_MAKER_PDF,
    ;

    companion object {
        fun from(type: String): ServiceType {
            return ServiceType.valueOf(type.uppercase())
        }

    }
}

