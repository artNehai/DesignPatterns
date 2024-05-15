package creational.prototype

interface Meal {
    val portionSize: String
    val extraIngredients: String?
    fun clone(): Meal
}

class Pasta(
    portion: String = "Medium",
    extra: String? = null,
) : Meal {

    override val portionSize = portion
    override val extraIngredients = extra

    private constructor(pasta: Pasta) : this(
        portion = pasta.portionSize,
        extra = pasta.extraIngredients,
    )

    override fun clone(): Meal = Pasta(this)

    override fun toString() = "$portionSize pasta".addIfPresent(extraIngredients)
}

class Espresso(
    private val isWithSugar: Boolean = false,
) : Meal {

    override val portionSize = "Small"
    override val extraIngredients = if (isWithSugar) "sugar" else null

    private constructor(espresso: Espresso) : this(
        isWithSugar = espresso.isWithSugar
    )

    override fun clone(): Meal = Espresso(this)

    override fun toString() = "Espresso".addIfPresent(extraIngredients)
}