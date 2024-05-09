package creational.abstract_factory

interface BMWFactory {
    fun createEngine(): Engine
    fun createIgnitionSystem(): IgnitionSystem
}

class BMWBerlinFactory : BMWFactory {
    override fun createEngine(): Engine = DieselEngine()
    override fun createIgnitionSystem(): IgnitionSystem = CompressionIgnition()
}

class BMWFrankfurtFactory : BMWFactory {
    override fun createEngine(): Engine = GasolineEngine()
    override fun createIgnitionSystem(): IgnitionSystem = SparkIgnition()
}