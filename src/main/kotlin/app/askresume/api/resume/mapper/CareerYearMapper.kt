package app.askresume.api.resume.mapper

import org.springframework.stereotype.Component

@Component
class CareerYearMapper {

    fun toCareer(careerYear: Int): String {
        return when (careerYear) {
            0 -> "newcomer"
            10 -> "more than 10 years"
            else -> "${careerYear}year"
        }
    }

}

