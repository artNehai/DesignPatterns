package creational.factory_method

abstract class BMWFactory {

    private var engine: Engine? = null

    protected abstract fun produceEngine(): Engine

    fun tuneEngine(): String {
        engine = produceEngine()
        return engine!!.tune()
    }

    fun deliverEngine(): String =
        if (engine == null) {
            "You forgot to tune the engine!"
        } else {
            engine = null
            "Successfully delivered"
        }
}

class BMWBerlinFactory : BMWFactory() {
    override fun produceEngine() = DieselEngine()
}

class BMWFrankfurtFactory : BMWFactory() {
    override fun produceEngine() = GasolineEngine()
}