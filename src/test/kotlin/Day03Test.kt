package ktkatas

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day03Test {
    private val theSample = readResource(javaClass, "sample.txt")
    private val theInput = readResource(javaClass, "input.txt")

    private fun computePart1(input: String = theInput): Int {
        val parsed = Parsed.parse(input)

        return parsed.symbols.flatMap { symbol ->
            parsed.findNumbersAround(symbol)
        }.distinct().sumOf { number ->
            number.value
        }
    }

    private fun computePart2(input: String = theInput): Int {
        val parsed = Parsed.parse(input)

        val possibleGears = parsed.symbols.filter { it.symbol == '*' }

        val gears = buildList {
            possibleGears.forEach { maybeGear ->
                val numbers = parsed.findNumbersAround(maybeGear)

                if (numbers.size == 2) add(maybeGear to numbers)
            }
        }

        return gears.sumOf { (_, numbers) ->
            numbers.map { number ->
                number.value
            }.reduce { result, currentNumber ->
                result * currentNumber
            }
        }
    }

    private data class ParsedSymbol(val symbol: Char, val position: Vec2)
    private data class ParsedNumber(val position: Vec2, val value: Int)

    private class Parsed(input: String) {
        private val inputLines = input.lines()
        private val height = inputLines.size
        private val width = inputLines.first().length

        private val positions: Set<Vec2> = run {
            val xs = 0..<width
            val ys = 0..height

            xs.flatMap { x ->
                ys.map { y -> Vec2(x, y) }
            }
        }.toSet()

        val symbols: List<ParsedSymbol>
            get() {
                return buildList {
                    positions.forEach { position ->
                        findSymbolAt(position)?.let { add(it) }
                    }
                }
            }

        private fun findSymbolAt(position: Vec2): ParsedSymbol? {
            if (findDigitAt(position) != null) return null

            val char = findCharAtPosition(position) ?: return null
            if (char == '.') return null

            return ParsedSymbol(symbol = char, position = position)
        }

        fun findNumbersAround(symbol: ParsedSymbol): List<ParsedNumber> {
            return buildList {
                val position = symbol.position
                findNumberAt(position.copy(x = position.x + 1))?.let { add(it) }
                findNumberAt(position.copy(x = position.x - 1))?.let { add(it) }
                // below
                findNumberAt(position.copy(y = position.y + 1))?.let { add(it) }
                findNumberAt(position.copy(x = position.x - 1, y = position.y + 1))?.let { add(it) }
                findNumberAt(position.copy(x = position.x + 1, y = position.y + 1))?.let { add(it) }
                // above
                findNumberAt(position.copy(y = position.y - 1))?.let { add(it) }
                findNumberAt(position.copy(x = position.x - 1, y = position.y - 1))?.let { add(it) }
                findNumberAt(position.copy(x = position.x + 1, y = position.y - 1))?.let { add(it) }
            }.distinct()
        }

        private fun findNumberAt(position: Vec2): ParsedNumber? {
            if (findDigitAt(position) == null) return null

            val toTheRight = position.copy(x = position.x + 1)
            if (findDigitAt(toTheRight) == null) {
                return parseNumberEndingAt(position)
            }

            return findNumberAt(toTheRight)
        }

        private fun parseNumberEndingAt(position: Vec2): ParsedNumber? {
            val digit = findDigitAt(position) ?: return null
            val prev = parseNumberEndingAt(position.copy(x = position.x - 1))

            if (prev != null) {
                return ParsedNumber(prev.position, prev.value * 10 + digit)
            }

            return ParsedNumber(position, digit)
        }

        private fun findDigitAt(position: Vec2): Int? {
            return findCharAtPosition(position).toString().toIntOrNull()
        }

        private fun findCharAtPosition(position: Vec2): Char? {
            if (position.x < 0 || position.x >= width) return null
            if (position.y < 0 || position.y >= height) return null
            return inputLines[position.y][position.x]
        }

        companion object {
            fun parse(input: String): Parsed {
                return Parsed(input)
            }
        }
    }

    @Nested
    inner class TddPart1And2 {
        @TestFactory
        fun `single line - single digit`() = listOf(
            "1" to 0,
            "*1" to 1,
            "1*" to 1,
            "2*" to 2,
            ".1*" to 1,
            "*" to 0,
            "*.1" to 0,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("input: $input, expected: $expected") {
                assertThat(computePart1(input)).isEqualTo(expected)
            }
        }

        @TestFactory
        fun `single line - multiple digit`() = listOf(
            "12" to 0,
            "12*" to 12,
            "123*" to 123,
            "*12" to 12,
            "*123" to 123,
            "...*123" to 123,
            "123*..." to 123,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("input: $input, expected: $expected") {
                assertThat(computePart1(input)).isEqualTo(expected)
            }
        }

        @TestFactory
        fun `numbers on line below`() = listOf(
            "*\n1" to 1,
            ".*.\n.1." to 1,
            ".*.\n1.." to 1,
            ".*.\n..1" to 1,
            "..*..\n12..." to 12,
            "..*..\n...12" to 12,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("input: ${input.replace("\n", "|")}, expected: $expected") {
                assertThat(computePart1(input)).isEqualTo(expected)
            }
        }

        @TestFactory
        fun `numbers on line above`() = listOf(
            "1\n*" to 1,
            ".1.\n.*." to 1,
            ".1.\n*.." to 1,
            ".1.\n..*" to 1,
            "12...\n..*.." to 12,
            "...12\n..*.." to 12,
        ).map { (input, expected) ->
            DynamicTest.dynamicTest("input: ${input.replace("\n", "|")}, expected: $expected") {
                assertThat(computePart1(input)).isEqualTo(expected)
            }
        }
    }

    @Test
    fun `part 1 - sample`() {
        assertThat(computePart1(theSample)).isEqualTo(4361)
    }

    @Test
    fun `part 1`() {
        assertThat(computePart1()).isEqualTo(553825)
    }

    @Test
    fun `part 2 - sample`() {
        assertThat(computePart2(theSample)).isEqualTo(467835)
    }

    @Test
    fun `part 2`() {
        assertThat(computePart2()).isEqualTo(93994191)
    }
}