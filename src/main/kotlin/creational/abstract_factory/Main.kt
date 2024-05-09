package creational.abstract_factory

import util.trace

fun main() {
    val location = "Frankfurt"
    val factory: BMWFactory =
        when (location) {
            "Berlin" -> BMWBerlinFactory()
            "Frankfurt" -> BMWFrankfurtFactory()
            else -> {
                println("New factory is being built")
                return
            }
        }

    // Production
    val engine = factory.createEngine()
    val ignitionSystem = factory.createIgnitionSystem()
    trace(engine.info())
    trace(ignitionSystem.ignite())

    trace eq """
        Engine that works with gasoline
        Ignition system: Spark!!
    """
}