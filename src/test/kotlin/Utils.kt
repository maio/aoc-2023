package ktkatas

object Utils

fun readResource(name: String): String {
    val resource = Utils.javaClass.getResource(name) ?: error("Resource $name not found")
    return resource.readText()
}

fun readResource(testClass: Class<*>, name: String): String {
    return readResource("/${testClass.simpleName}/${name}")
}

data class Vec2(val x: Int, val y: Int)
