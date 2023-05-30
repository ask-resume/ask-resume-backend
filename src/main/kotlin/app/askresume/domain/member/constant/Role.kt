package app.askresume.domain.member.constant


enum class Role {

    USER, ADMIN;

    companion object {
        fun from(role: String): Role = valueOf(role)
    }
}

