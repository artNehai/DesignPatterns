package creational.builder

class CoffeeBuilder {
    private var result = Coffee()

    fun reset() {
        result = Coffee()
    }
    fun addEspresso(portions: Int) {
        result.espressoPortions += portions
    }
    fun addWater(mg: Int) {
        result.waterMg += mg
    }
    fun addMilk(mg: Int) {
        result.milkMg += mg
    }
    fun addSugar(portions: Int) {
        result.sugarPortions += portions
    }
    fun getResult() = result
}

data class Coffee(
    var espressoPortions: Int = 0,
    var waterMg: Int = 0,
    var milkMg: Int = 0,
    var sugarPortions: Int = 0,
) {
    override fun toString() =
        when {
            waterMg != 0 -> "Americano: $sugarPortions sugar"
            milkMg != 0 -> "Cappuccino: $sugarPortions sugar"
            else -> "Coffee isn't ready"
        }
}