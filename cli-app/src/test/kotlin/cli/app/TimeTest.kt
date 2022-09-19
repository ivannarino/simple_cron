package cli.app

import org.junit.Assert.assertTrue
import org.junit.Test

class TimeTest {

    @Test
    fun shouldBeLess() {
        // given
        val other = Time(59, 10)
        val subject = Time(58, 10)

        // when
        val result = subject < other

        // then
        assertTrue(result)
    }

    @Test
    fun shouldBeMore() {
        // given
        val other = Time(59, 10)
        val subject = Time(0, 11)

        // when
        val result = subject > other

        // then
        assertTrue(result)
    }

    @Test
    fun shouldBeEqual() {
        // given
        val other = Time(59, 10)
        val subject = Time(59, 10)

        // when
        val result = subject == other

        // then
        assertTrue(result)
    }
}