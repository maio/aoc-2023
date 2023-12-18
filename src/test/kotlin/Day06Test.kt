package ktkatas

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day06Test {
    private val theSample = readResource(javaClass, "sample.txt")
    private val theInput = readResource(javaClass, "input.txt")

    private fun computePart1(input: String): Int = input.length
    private fun computePart2(input: String): Int = input.length


    class Parsed(input: String) {
        private val lines = input.lines()
        private val timeString = lines.first()
        private val distanceString = lines.drop(1).first()

        val times = timeString.split(":").drop(1).first().toListOfInts()
        val distances = distanceString.split(":").drop(1).first().toListOfInts()
    }

    @Test
    fun `part 1 - sample`() {
        assertThat(computePart1(theSample)).isEqualTo(288)
    }

    @Test
    fun `part 1`() {
        assertThat(computePart1(theInput)).isEqualTo(0)
    }

    @Test
    fun `part 2`() {
        assertThat(computePart2(theInput)).isEqualTo(0)
    }
}