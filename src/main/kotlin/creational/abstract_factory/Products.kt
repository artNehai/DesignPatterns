package creational.abstract_factory

interface Engine {
    fun info(): String
}

class DieselEngine : Engine {
    override fun info() = "Engine that works with diesel"
}

class GasolineEngine : Engine {
    override fun info() = "Engine that works with gasoline"
}

interface IgnitionSystem {
    fun ignite(): String
}

class CompressionIgnition : IgnitionSystem {
    override fun ignite() = "Ignition system: Compress fuel"
}

class SparkIgnition : IgnitionSystem {
    override fun ignite() = "Ignition system: Spark!!"
}
