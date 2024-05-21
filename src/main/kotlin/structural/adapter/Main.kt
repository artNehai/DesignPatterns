package structural.adapter

import util.trace

class EuroSocket(
    private val euroPlug: EuroPlug,
) {
    fun connect() = """
        My plug has ${euroPlug.poles}.
        Fortunately, I didn't forget the adapter!
    """.trimIndent()
}

fun main() {
    val myEuroPlugAdapter = EuroPlugAdapter()
    val euroSocket = EuroSocket(euroPlug = myEuroPlugAdapter)
    trace(euroSocket.connect())

    trace eq """
        My plug has two round poles.
        Fortunately, I didn't forget the adapter!
    """
}