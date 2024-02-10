package app.askresume

import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import java.util.*
import kotlin.reflect.KClass


object RANDOM {
    fun nextString(minSize: Int = 1, maxSize: Int = 1000): String = EasyRandomParameters()
        .stringLengthRange(minSize, maxSize)
        .seed(Random().nextLong(Long.MAX_VALUE))
        .let {
            EasyRandom(it)
        }.nextObject(String::class.java)

    fun nextEmail(): String =
        "${nextString(EMAIL_MIN_LENGTH, EMAIL_MAX_LENGTH)}${TEST_DOMAIN}"

    fun nextInt(
        minSize: Int = 0,
        maxSize: Int = Int.MAX_VALUE
    ): Int = Random().nextInt(minSize, maxSize)

    fun nextLong(
        minSize: Long = 0,
        maxSize: Long = Long.MAX_VALUE
    ): Long = Random().nextLong(minSize, maxSize)

    fun <T : Any> nextObject(`class`: KClass<T>): T {
        val easyRandom = EasyRandom(EasyRandomParameters())
        return easyRandom.nextObject(`class`.java)
    }

    fun <T : Any> nextList(`class`: KClass<T>, size: Int = 10): List<T> {
        val easyRandom = EasyRandom(EasyRandomParameters())
        val mutableList = mutableListOf<T>()

        repeat(size) {
            val randomObject = easyRandom.nextObject(`class`.java)
            mutableList.add(randomObject)
        }
        return mutableList.toList()
    }

    private const val EMAIL_MIN_LENGTH = 5
    private const val EMAIL_MAX_LENGTH = 15

    private const val TEST_DOMAIN = "@mail.com"
}