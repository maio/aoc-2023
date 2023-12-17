package ktkatas

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day0XTest {
    private val theSample = readResource(javaClass, "sample.txt")
    private val theInput = readResource(javaClass, "input.txt")

    private fun computePart1(input: String): Int = input.length
    private fun computePart2(input: String): Int = input.length

    @Test
    fun `part 1 - sample`() {
        assertThat(computePart1(theSample)).isEqualTo(0)
    }

    @Test
    fun `part 1`() {
        assertThat(computePart1(theInput)).isEqualTo(0)
    }

    @Test
    fun `part 2`() {
        assertThat(computePart2(theInput)).isEqualTo(0)
    }

    @Nested
    inner class UtilsTests {
    }
}