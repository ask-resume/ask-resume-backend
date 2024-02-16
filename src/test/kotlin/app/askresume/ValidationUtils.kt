package app.askresume

import javax.validation.ConstraintViolation
import javax.validation.Validation

object ValidationUtils {

    fun <T> validate(obj: T): MutableSet<ConstraintViolation<T>>? {
        val validator = Validation.buildDefaultValidatorFactory().validator
        return validator.validate(obj)
    }
}