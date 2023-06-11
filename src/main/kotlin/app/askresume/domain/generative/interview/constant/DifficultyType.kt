package app.askresume.domain.generative.interview.constant

import java.util.*


enum class DifficultyType(private val type: String) {

    EASY("easy"), MEDIUM("medium"), HARD("hard");

    fun type() = type

    companion object {
        fun isDifficultyType(type: String): Boolean {
            val difficultyTypes = Arrays.stream(values())
                .filter { difficultyType -> difficultyType.name.equals(type, ignoreCase = true) }.toList()
            return difficultyTypes.size != 0
        }
    }
}