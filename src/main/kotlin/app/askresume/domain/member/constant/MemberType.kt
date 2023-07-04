package app.askresume.domain.member.constant

enum class MemberType {

    GOOGLE,
    LINKED_IN,
    ;

    companion object {
        fun from(type: String): MemberType {
            return MemberType.valueOf(type.uppercase())
        }

        fun isMemberType(type: String): Boolean {
            return MemberType.values().any { it.name == type.uppercase() }
        }
    }

}