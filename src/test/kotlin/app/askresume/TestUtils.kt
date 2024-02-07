package app.askresume

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import java.util.Random


object TestUtils {

    fun randomString(minSize: Int, maxSize: Int): String = EasyRandomParameters()
            .stringLengthRange(minSize, maxSize)
            .seed(Random().nextLong(Long.MAX_VALUE))
            .let {
                EasyRandom(it)
            }.nextObject(String::class.java)

    fun randomEmail(): String =
        "${randomString(EMAIL_MIN_LENGTH, EMAIL_MAX_LENGTH)}${TEST_DOMAIN}"

    fun randomInt(minSize: Int, maxSize: Int): Int = Random().nextInt(minSize, maxSize)
    fun randomLong(minSize: Long, maxSize: Long): Long = Random().nextLong(minSize, maxSize)

    private const val EMAIL_MIN_LENGTH = 5
    private const val EMAIL_MAX_LENGTH = 15

    private const val TEST_DOMAIN = "@mail.com"
}