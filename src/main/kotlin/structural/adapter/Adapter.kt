package structural.adapter

interface EuroPlug {
    val poles: String
}

class AmericanPlug {
    val prongs = "two flat prongs"
}

class EuroPlugAdapter : EuroPlug {
    private val americanPlug = AmericanPlug()

    override val poles = americanPlug.prongs
        .replace(oldValue = "flat", newValue = "round")
        .replace(oldValue = "prongs", newValue = "poles")
}