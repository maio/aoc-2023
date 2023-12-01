package ktkatas

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day01Test {
    private val input = readResource(javaClass, "input.txt")

    private fun computePart1() = input.lines()
        .map(::lineToNumber)
        .sum()

    private fun computePart2() = input.lines()
        .map(::replaceTextWithDigit)
        .map(::lineToNumber)
        .sum()

    private fun lineToNumber(line: String): Int {
        val digitsOnly = line.filter(Char::isDigit)

        return "${digitsOnly.first()}${digitsOnly.last()}".toInt()
    }

    private fun replaceTextWithDigit(line: String): String {
        val replaceWith = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )

        if (line.isEmpty()) return ""

        replaceWith.forEach { (text, digit) ->
            if (line.startsWith(text)) {
                return digit + replaceTextWithDigit(line.drop(1))
            }
        }

        return line.take(1) + replaceTextWithDigit(line.drop(1))
    }

    @Test
    fun `part 1`() {
        assertThat(computePart1()).isEqualTo(55386)
    }

    @Test
    fun `part 2`() {
        assertThat(computePart2()).isEqualTo(54824)
    }

    @Nested
    inner class UtilsTests {
        @Test
        fun `replace text with digit`() {
            assertThat(replaceTextWithDigit("one")).isEqualTo("1ne")
            assertThat(replaceTextWithDigit("1")).isEqualTo("1")
            assertThat(replaceTextWithDigit("one1")).isEqualTo("1ne1")
            assertThat(replaceTextWithDigit("oneone")).isEqualTo("1ne1ne")
            assertThat(replaceTextWithDigit("one1one")).isEqualTo("1ne11ne")
            assertThat(replaceTextWithDigit("eight")).isEqualTo("8ight")
            assertThat(replaceTextWithDigit("two")).isEqualTo("2wo")
            assertThat(replaceTextWithDigit("aonea")).isEqualTo("a1nea")
            assertThat(replaceTextWithDigit("eightt")).isEqualTo("8ightt")
            assertThat(replaceTextWithDigit("sevenine")).isEqualTo("7eve9ine")
            assertThat(replaceTextWithDigit("eightwothree")).isEqualTo("8igh2wo3hree")
            assertThat(replaceTextWithDigit("twonethree")).isEqualTo("2w1ne3hree")
        }
    }
}