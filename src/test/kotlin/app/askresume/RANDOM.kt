package app.askresume

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import java.util.*
import kotlin.reflect.KClass


object RANDOM {
    fun nextString() = this.nextString(DEFAULT_STRING_MIN_LENGTH, DEFAULT_STRING_MAX_LENGTH)

    fun nextString(minSize: Int, maxSize: Int): String = EasyRandomParameters()
        .stringLengthRange(minSize, maxSize)
        .seed(Random().nextLong(Long.MAX_VALUE))
        .let {
            EasyRandom(it)
        }.nextObject(String::class.java)

    fun nextEmail(): String =
        "${nextString(EMAIL_MIN_LENGTH, EMAIL_MAX_LENGTH)}${TEST_DOMAIN}"


    fun nextInt() = this.nextInt(0, Int.MAX_VALUE)

    fun nextInt(minSize: Int, maxSize: Int): Int = Random().nextInt(minSize, maxSize)

    fun nextLong() = this.nextLong(0, Long.MAX_VALUE)
    fun nextLong(minSize: Long, maxSize: Long): Long = Random().nextLong(minSize, maxSize)

    fun <T : Any> nextObject(kClass: KClass<T>): T {
        val easyRandom = EasyRandom(EasyRandomParameters())
        val javaClass = kClass.java
        return easyRandom.nextObject(javaClass)
    }


    private const val DEFAULT_STRING_MIN_LENGTH = 1
    private const val DEFAULT_STRING_MAX_LENGTH = 1000

    private const val EMAIL_MIN_LENGTH = 5
    private const val EMAIL_MAX_LENGTH = 15

    private const val TEST_DOMAIN = "@mail.com"
}