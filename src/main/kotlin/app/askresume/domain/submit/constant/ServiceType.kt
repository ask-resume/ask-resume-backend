package app.askresume.domain.submit.constant

enum class ServiceType {

    INTERVIEW_MAKER,
    ;

    companion object {
        fun from(type: String): ServiceType {
            return ServiceType.valueOf(type.uppercase())
        }

        fun isServiceType(type: String): Boolean {
            return ServiceType.values().any { it.name == type.uppercase() }
        }
    }
}

