package ktkatas

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class Day02Test {
    private val input = readResource(javaClass, "input.txt")
    private val sample = readResource(javaClass, "sample.txt")

    private fun computePart1(s: String = input): Int {
        return getGameLines(s).sumOf { gameLine ->
            val isPossible = getSets(gameLine).all { set ->
                getKindsInASet(set).all { (kind, count) ->
                    isPossible(kind, count)
                }
            }

            if (!isPossible) 0 else getGameId(gameLine)
        }
    }

    private fun isPossible(kind: String, count: Int): Boolean {
        if (kind == "red" && count > 12) return false
        if (kind == "green" && count > 13) return false
        if (kind == "blue" && count > 14) return false
        return true
    }

    private fun getGameLines(s: String) = s.lines().filter { it.isNotBlank() }
    private fun getGameId(s: String) = s.split(":").first()
        .removePrefix("Game ").toInt()

    private fun getSets(line: String) = line.split(": ").drop(1).take(1).single()
        .split("; ")

    private fun getKindsInASet(set: String) = set.split(", ").map {
        val (count, kind) = it.split(" ")
        kind to count.toInt()
    }

    private fun computePart2(s: String = input): Int {
        return getGameLines(s).sumOf(::computePart2SingleGame)
    }

    private fun computePart2SingleGame(gameLine: String): Int {
        var minRed = 0
        var minGreen = 0
        var minBlue = 0

        getSets(gameLine).map { set ->
            getKindsInASet(set).forEach { (kind, count) ->
                if (kind == "red") minRed = minRed.coerceAtLeast(count)
                if (kind == "green") minGreen = minGreen.coerceAtLeast(count)
                if (kind == "blue") minBlue = minBlue.coerceAtLeast(count)
            }
        }

        return minRed.coerceAtLeast(1) *
                minGreen.coerceAtLeast(1) *
                minBlue.coerceAtLeast(1)
    }

    @Test
    fun `part 2 TDD`() {
        assertThat(computePart2("Game 1: 1 red")).isEqualTo(1)
        assertThat(computePart2("Game 1: 2 red")).isEqualTo(2)
        assertThat(computePart2("Game 1: 2 green")).isEqualTo(2)
        assertThat(computePart2("Game 1: 2 blue")).isEqualTo(2)
        assertThat(computePart2("Game 1: 1 red; 2 red")).isEqualTo(2)
        assertThat(computePart2("Game 1: 1 red; 2 red; 3 red")).isEqualTo(3)
        assertThat(computePart2("Game 1: 2 red; 3 green")).isEqualTo(6)
        assertThat(computePart2("Game 1: 2 red; 3 green")).isEqualTo(6)
        assertThat(computePart2("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")).isEqualTo(48)
        assertThat(computePart2("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue")).isEqualTo(12)
        assertThat(computePart2("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"))
            .isEqualTo(1560)
        assertThat(computePart2("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"))
            .isEqualTo(630)
        assertThat(computePart2("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"))
            .isEqualTo(36)

        assertThat(computePart2("Game 1: 2 red\nGame 2: 3 red")).isEqualTo(5)
    }

    @Test
    fun `part 1 TDD`() {
        assertThat(computePart1("Game 1: 1 red")).isEqualTo(1)
        assertThat(computePart1("Game 2: 1 red")).isEqualTo(2)
        assertThat(computePart1("Game 1: 12 red")).isEqualTo(1)
        assertThat(computePart1("Game 1: 13 red")).isEqualTo(0)
        assertThat(computePart1("Game 1: 13 green")).isEqualTo(1)
        assertThat(computePart1("Game 1: 14 green")).isEqualTo(0)
        assertThat(computePart1("Game 1: 14 blue")).isEqualTo(1)
        assertThat(computePart1("Game 1: 15 blue")).isEqualTo(0)
        assertThat(computePart1("Game 1: 1 red, 1 green")).isEqualTo(1)
        assertThat(computePart1("Game 1: 13 red, 1 green")).isEqualTo(0)
        assertThat(computePart1("Game 1: 1 red, 14 green")).isEqualTo(0)
        assertThat(computePart1("Game 1: 1 red; 1 red")).isEqualTo(1)
        assertThat(computePart1("Game 1: 1 red; 13 red")).isEqualTo(0)
        assertThat(computePart1("Game 1: 1 red\nGame 2: 1 red")).isEqualTo(3)
    }

    @Test
    fun `part 1`() {
        assertThat(computePart1(sample)).isEqualTo(8)
        assertThat(computePart1(input)).isEqualTo(2156)
    }

    @Test
    fun `part 2`() {
        assertThat(computePart2(sample)).isEqualTo(2286)
        assertThat(computePart2(input)).isEqualTo(66909)
    }

    @Nested
    inner class UtilsTests {
        private val input = """Game 1: 1 blue"""

        @Test
        fun `part 1`() {


        }
    }
}