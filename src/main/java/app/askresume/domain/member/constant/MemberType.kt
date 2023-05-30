package app.askresume.domain.member.constant

import java.util.*

enum class MemberType {

    LOCAL, GOOGLE;

    companion object {
        fun from(type: String): MemberType {
            return valueOf(type.uppercase(Locale.getDefault()))
        }

        fun isMemberType(type: String): Boolean {
            val memberTypes = Arrays.stream(values())
                .filter { memberType -> memberType.name.equals(type, ignoreCase = true) }.toList()
            return memberTypes.size != 0
        }
    }

}