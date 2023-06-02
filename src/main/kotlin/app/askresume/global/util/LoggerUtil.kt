package app.askresume.global.util

import org.slf4j.LoggerFactory

object LoggerUtil {
    inline fun <reified T> T.log(): org.slf4j.Logger = LoggerFactory.getLogger(T::class.java)
}