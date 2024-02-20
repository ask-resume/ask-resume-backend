package app.askresume

import javax.validation.ConstraintViolation
import javax.validation.Validation

object ValidationUtils {

    fun <T> validate(obj: T): MutableSet<ConstraintViolation<T>> = Validation.buildDefaultValidatorFactory().validator
        .run { this.validate(obj) }
}