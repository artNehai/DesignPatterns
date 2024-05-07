package creational.factory_method

import util.trace

fun main() {
    val localCFO = "CFO Berlin"
    val factory: BMWFactory =
        when (localCFO) {
            "CFO Berlin" -> BMWBerlinDieselFactory()
            "CFO Frankfurt" -> BMWFrankfurtGasolineFactory()
            else -> {
                println("New factory is being build")
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