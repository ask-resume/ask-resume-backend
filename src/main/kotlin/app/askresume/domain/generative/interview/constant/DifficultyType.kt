package app.askresume.domain.generative.interview.constant

import java.util.*


enum class DifficultyType(
    private val value: String
) {

    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard")
    ;

    fun value() = value

    companion object {
        fun isDifficultyType(type: String): Boolean {
            val difficultyTypes = Arrays.stream(values())
                .filter { difficultyType -> difficultyType.name.equals(type, ignoreCase = true) }.toList()
            return difficultyTypes.size != 0
        }
    }
}