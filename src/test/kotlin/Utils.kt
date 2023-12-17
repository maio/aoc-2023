package ktkatas

object Utils

fun readResource(name: String): String {
    val resource = Utils.javaClass.getResource(name) ?: error("Resource $name not found")
    return resource.readText()
}

fun readResource(testClass: Class<*>, name: String): String {
    return readResource("/${testClass.simpleName}/${name}")
}

fun String.toListOfInts() = trim().split("\\s+".toRegex()).map { it.toInt() }

data class Vec2(val x: Int, val y: Int) {
    val neighbours by lazy {
        listOf(
            left(), right(),
            up(), down(),
            left().up(), left().down(),
            right().up(), right().down(),
        )
    }

    fun right() = copy(x = x + 1)
    fun left() = copy(x = x - 1)
    fun up() = copy(y = y + 1)
    fun down() = copy(y = y - 1)
}
