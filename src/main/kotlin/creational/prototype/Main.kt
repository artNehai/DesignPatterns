package creational.prototype

import util.trace

fun main() {
    // Order by the first customer
    val order1: List<Meal> = listOf(Pasta(), Espresso(isWithSugar = true))
    trace("First order: ${order1.joinToString()}")

    // The second customer ordered the same
    val order2: List<Meal> = order1.map(Meal::clone)
    trace("Second order: ${order2.joinToString()}")

    trace eq """
        First order: Medium pasta, Espresso with sugar
        Second order: Medium pasta, Espresso with sugar
    """
}