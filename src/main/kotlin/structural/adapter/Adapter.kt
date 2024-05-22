package structural.adapter

import util.trace

interface EuroPlug {
    val poles: String
}

class AmericanPlug {
    val prongs = "two flat prongs"
}

class EuroPlugAdapter : EuroPlug {
    private val americanPlug = AmericanPlug()

    override val poles = americanPlug.prongs
        .replace(oldValue = "flat", newValue = "round")
        .replace(oldValue = "prongs", newValue = "poles")
}

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