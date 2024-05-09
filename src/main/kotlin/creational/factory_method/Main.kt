package creational.factory_method

import util.trace

fun main() {
    val manager = "Manager in Berlin"
    val factory: BMWFactory =
        when (manager) {
            "Manager in Berlin" -> BMWBerlinFactory()
            "Manager in Frankfurt" -> BMWFrankfurtFactory()
            else -> {
                println("New factory is being built")
                return
            }
        }

    // Today work done by the factory
    trace(factory.deliverEngine())
    trace(factory.tuneEngine())
    trace(factory.deliverEngine())

    trace eq """
        You forgot to tune the engine!
        Engine is tuned. Works with diesel
        Successfully delivered
    """
}