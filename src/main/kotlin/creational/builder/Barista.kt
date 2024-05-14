package creational.builder

class Barista(
    private val builder: CoffeeBuilder,
) {
    fun makeAmericano() {
        builder.reset()
        builder.addWater(mg = 50)
        builder.addEspresso(portions = 1)
    }

    fun makeCappuccino() {
        builder.reset()
        builder.addEspresso(portions = 2)
        builder.addMilk(mg = 150)
    }
}