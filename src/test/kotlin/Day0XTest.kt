package ktkatas

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day0XTest {
    private val input = readResource(javaClass, "input.txt")

    private fun computePart1(): Int = input.length

    private fun computePart2(): Int = input.length

    @Test
    fun `part 1`() {
        assertThat(computePart1()).isEqualTo(0)
    }

    @Test
    fun `part 2`() {
        assertThat(computePart2()).isEqualTo(0)
    }

    @Nested
    inner class UtilsTests {
    }
}