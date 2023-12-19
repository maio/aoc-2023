package ktkatas

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class Day06Test {
    private val theSample = readResource(javaClass, "sample.txt")
    private val theInput = readResource(javaClass, "input.txt")

    private fun computePart1(input: String): Int {
        return Parsed(input).races.map { race ->
            val possibleHoldTimes = (1..<race.time)

            val waysToWin = possibleHoldTimes.filter { holdTime ->
                val speed = holdTime
                (speed * (race.time - holdTime)) >= race.distance
            }
            waysToWin.size
        }.reduce { acc, i -> acc * i }
    }

    private fun computePart2(input: String): Int {
        return Parsed2(input).race.let { race ->
            val possibleHoldTimes = (1..<race.time)

            val waysToWin = possibleHoldTimes.filter { holdTime ->
                val speed = holdTime
                (speed * (race.time - holdTime)) >= race.distance
            }
            waysToWin.size
        }
    }

    class Parsed(input: String) {
        data class Race(val time: Int, val distance: Int)

        private val lines = input.lines()
        private val timeString = lines.first()
        private val distanceString = lines.drop(1).first()
        private val times = timeString.split(":").drop(1).first().toListOfInts()
        private val distances = distanceString.split(":").drop(1).first().toListOfInts()

        val races = times.zip(distances).map { Race(time = it.first, distance = it.second) }
    }

    class Parsed2(input: String) {
        data class Race(val time: Long, val distance: Long)

        private val lines = input.lines()
        private val timeString = lines.first()
        private val distanceString = lines.drop(1).first()
        private val time = timeString.split(":").drop(1).first().replace("\\s+".toRegex(), "").toLong()
        private val distance = distanceString.split(":").drop(1).first().replace("\\s+".toRegex(), "").toLong()

        val race = Race(time = time, distance = distance)
    }

    @Nested
    inner class Tdd {
        @TestFactory
        fun `part 1 - single race - distance 1`() = listOf(
            Parsed.Race(time = 1, distance = 1) to 0, //
            Parsed.Race(time = 2, distance = 1) to 1, // 1
            Parsed.Race(time = 3, distance = 1) to 2, // 1, 2
            Parsed.Race(time = 4, distance = 1) to 3, // 1, 2, 3
            Parsed.Race(time = 5, distance = 1) to 4, // 1, 2, 3, 4
        ).map { (race, result) ->
            dynamicTest(race.toString()) {
                assertThat(
                    computePart1(
                        """
                            |Time:      ${race.time}
                            |Distance:  ${race.distance}
                        """.trimMargin()
                    )
                ).isEqualTo(result)
            }
        }

        @TestFactory
        fun `part 1 - single race - distance 2`() = listOf(
            Parsed.Race(time = 2, distance = 2) to 0, //
            Parsed.Race(time = 3, distance = 2) to 2, // 1, 2
            Parsed.Race(time = 4, distance = 2) to 3, // 1, 2, 3
            Parsed.Race(time = 5, distance = 2) to 4, // 1, 2, 3, 4
        ).map { (race, result) ->
            dynamicTest(race.toString()) {
                assertThat(
                    computePart1(
                        """
                            |Time:      ${race.time}
                            |Distance:  ${race.distance}
                        """.trimMargin()
                    )
                ).isEqualTo(result)
            }
        }

        @TestFactory
        fun `part 1 - single race - samples`() = listOf(
            Parsed.Race(time = 7, distance = 9) to 4,
            Parsed.Race(time = 15, distance = 40) to 8,
            Parsed.Race(time = 30, distance = 200) to 11, // WTF - 9 @ https://adventofcode.com/2023/day/6
        ).map { (race, result) ->
            dynamicTest(race.toString()) {
                assertThat(
                    computePart1(
                        """
                            |Time:      ${race.time}
                            |Distance:  ${race.distance}
                        """.trimMargin()
                    )
                ).isEqualTo(result)
            }
        }
    }

    @Test
    fun `part 1 - sample`() {
        // // WTF - 288 @ https://adventofcode.com/2023/day/6
        assertThat(computePart1(theSample)).isEqualTo(352)
    }

    @Test
    fun `part 1`() {
        assertThat(computePart1(theInput)).isEqualTo(2344708)
    }

    @Test
    fun `part 2`() {
        assertThat(computePart2(theInput)).isEqualTo(30125202)
    }
}