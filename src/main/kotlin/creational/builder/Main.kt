package creational.builder

import util.trace

fun main() {
    val coffeeBuilder = CoffeeBuilder()
    val barista = Barista(coffeeBuilder)

    // Director class makes coffee
    barista.makeAmericano()
    trace(coffeeBuilder.getResult())

    // Client makes his adjustments
    coffeeBuilder.addSugar(portions = 2)
    trace(coffeeBuilder.getResult())

    trace eq """
        Americano: 0 sugar
        Americano: 2 sugar
    """
}