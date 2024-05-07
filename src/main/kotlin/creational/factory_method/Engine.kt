package creational.factory_method

interface Engine {
    fun tune(): String
}

class DieselEngine : Engine {
    override fun tune() = "Engine is tuned. Works with diesel"
}

class GasolineEngine : Engine {
    override fun tune() = "Engine is tuned. Works with gasoline"
}
