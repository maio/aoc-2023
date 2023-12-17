package ktkatas

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day04Test {
    private val theSample = readResource(javaClass, "sample.txt")
    private val theInput = readResource(javaClass, "input.txt")

    private fun computePart1(input: String): Int {
        return Parsed(input).cards.sumOf { card ->
            if (card.isWinning) {
                (1..card.getWinningNumbersCount()).reduce { result, i ->
                    result * 2
                }
            } else {
                0
            }
        }
    }

    private fun computePart2(input: String): Int {
        val parsed = Parsed(input)
        return computePart2Inner(parsed.cards.size, parsed, parsed.cards.map { it.id })
    }

    private tailrec fun computePart2Inner(result: Int = 0, parsed: Parsed, examine: List<Int>): Int {
        if (examine.isEmpty()) return result

        val winningIds = examine.filter {
            parsed.getCardById(it).isWinning
        }

        val wonCardIds = winningIds.flatMap { baseId ->
            (1..parsed.getCardById(baseId).getWinningNumbersCount()).map { offset ->
                baseId + offset
            }
        }

        return computePart2Inner(result + wonCardIds.size, parsed, wonCardIds)
    }

    class Parsed(input: String) {
        private val lines = input.lines()

        val cards: List<Card> = lines.map { line ->
            val (a, b) = line.split(": ")
            val (_, isString) = a.split("\\s+".toRegex())
            val (winningNumbersString, myNumbersString) = b.split(" | ")

            val winningNumbers = winningNumbersString.toListOfInts()
            val myNumbers = myNumbersString.toListOfInts()

            Card(
                id = isString.toInt(),
                winningNumbers = winningNumbers,
                myNumbers = myNumbers,
            )
        }

        fun getCardById(id: Int): Card {
            return cards[id - 1].also { check(it.id == id) }
        }
    }

    data class Card(
        val id: Int,
        val winningNumbers: List<Int>,
        val myNumbers: List<Int>
    ) {
        val isWinning = getWinningNumbersCount() > 0

        fun getWinningNumbersCount(): Int {
            return winningNumbers.count { winningNumber ->
                myNumbers.contains(winningNumber)
            }
        }
    }

    @Nested
    inner class Tdd {
        @Test
        fun `part 2 - first card wins some cards`() {
            computePart2(
                """
                    |Card 1: 1 | 1
                    |Card 2: 1 | 2
                """.trimMargin()
            ).let { result ->
                assertThat(result).isEqualTo(2 + 1)
            }

            computePart2(
                """
                    |Card 1: 1 2 | 1 2
                    |Card 2: 1 | 2
                    |Card 3: 1 | 2
                """.trimMargin()
            ).let { result ->
                assertThat(result).isEqualTo(3 + 2)
            }
        }

        @Test
        fun `part 2 - first two cards wins`() {
            computePart2(
                """
                    |Card 1: 1 | 1
                    |Card 2: 1 | 1
                    |Card 3: 1 | 2
                """.trimMargin()
            ).let { result ->
                assertThat(result).isEqualTo(3 + 1 + 1 + 1)
            }
        }
    }

    @Test
    fun `part 1 - sample`() {
        assertThat(computePart1(theSample)).isEqualTo(13)
    }

    @Test
    fun `part 1`() {
        assertThat(computePart1(theInput)).isEqualTo(25231)
    }

    @Test
    fun `part 2 - sample`() {
        assertThat(computePart2(theSample)).isEqualTo(30)
    }

    @Test
    fun `part 2`() {
        assertThat(computePart2(theInput)).isEqualTo(9721255)
    }

    @Nested
    inner class UtilsTests {
    }
}
