# Design Patterns In Kotlin
Inspired by [design-patterns-kotlin](https://github.com/mmolosay/design-patterns-kotlin "design-patterns-kotlin") by [mmolosay](https://github.com/mmolosay "mmolosay"), 
be sure to leave him a star.

## Table of Contents

* [Creational Patterns](#creational)
	* [Factory Method](#factory-method)
	* [Abstract Factory](#abstract-factory)
	* [Builder](#builder)
   	* [Prototype](#prototype)
   	* [Singleton](#singleton)
* [Structural Patterns](#structural)
  	* [Adapter](#adapter)
  	* [Bridge](#bridge)
  	* [Composite](#composite)
  	* [Decorator](#decorator)


Creational
==========
> In software engineering, creational design patterns are design patterns that deal with object creation mechanisms, trying to create objects in a manner suitable to the
> situation.The basic form of object creation could result in design problems or added complexity to the design. Creational design patterns solve this problem by somehow
> controlling this object creation.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Creational_pattern)


[Factory Method](/src/main/kotlin/creational/factory_method)
-----------------
Factory Method is a creational design pattern that provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created.

#### Premise
As the car company grows there is need to expand production with different types of engines. By implementing this pattern we ensure that the codebase isn't tied to any 
particular engine type, hence introduciton of a new type won't break the existing code.

#### Example
```kotlin
interface Engine {
    fun tune(): String
}

class DieselEngine : Engine {
    override fun tune() = "Engine is tuned. Works with diesel"
}

class GasolineEngine : Engine {
    override fun tune() = "Engine is tuned. Works with gasoline"
}

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
```

#### Usage
```kotlin
val manager = "Manager in Berlin"
val factory: BMWFactory =
    when (manager) {
        "Manager in Berlin" -> BMWBerlinFactory()
        "Manager in Frankfurt" -> BMWFrankfurtFactory()
        else -> {
            println("New factory is being built")
            return
        }
    }

// Today's work done by the factory
trace(factory.deliverEngine())
trace(factory.tuneEngine())
trace(factory.deliverEngine())
```

#### Trace
```
You forgot to tune the engine!
Engine is tuned. Works with diesel
Successfully delivered
```


[Abstract Factory](/src/main/kotlin/creational/abstract_factory)
-----------------
Abstract Factory is a creational design pattern that lets you produce families of related objects without specifying their concrete classes.

#### Premise
It's hard to imagine for a car company to produce only the engines. We want to extend the production with different kinds of ignition systems, each works with different type of engine. By implementing this pattern we don’t have to worry about creating the wrong ignation system which doesn’t match the engine already created.

#### Example
```kotlin
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
```

#### Usage
```kotlin
val location = "Frankfurt"
val factory: BMWFactory =
    when (location) {
        "Berlin" -> BMWBerlinFactory()
        "Frankfurt" -> BMWFrankfurtFactory()
        else -> {
            println("New factory is being built")
            return
        }
    }

// Production
val engine = factory.createEngine()
val ignitionSystem = factory.createIgnitionSystem()
trace(engine.info())
trace(ignitionSystem.ignite())
```

#### Trace
```
Engine that works with gasoline
Ignition system: Spark!!
```


[Builder](/src/main/kotlin/creational/builder)
-----------------
Builder is a creational design pattern that lets you construct complex objects step by step. The pattern allows you to produce different types and representations of an object using the same construction code.

#### Example
```kotlin
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
```

#### Usage
```kotlin
val coffeeBuilder = CoffeeBuilder()
val barista = Barista(coffeeBuilder)

// Director class makes coffee
barista.makeAmericano()
trace(coffeeBuilder.getResult())

// Client makes his adjustments
coffeeBuilder.addSugar(portions = 2)
trace(coffeeBuilder.getResult())
```

#### Trace
```
Americano: 0 sugar
Americano: 2 sugar
```


[Prototype](/src/main/kotlin/creational/prototype)
-----------------
Prototype is a creational design pattern that lets you copy existing objects without making your code dependent on their classes.

#### Example
```kotlin
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
```

#### Usage
```kotlin
// Order by the first customer
val order1: List<Meal> = listOf(Pasta(), Espresso(isWithSugar = true))
trace("First order: ${order1.joinToString()}")

// Second customer ordered the same
val order2: List<Meal> = order1.map(Meal::clone)
trace("Second order: ${order2.joinToString()}")
```

#### Trace
```
First order: Medium pasta, Espresso with sugar
Second order: Medium pasta, Espresso with sugar
```


[Singleton](/src/main/kotlin/creational/singleton/Singleton.kt)
-----------------
Singleton is a creational design pattern that lets you ensure that a class has only one instance, while providing a global access point to this instance.

#### Example
```kotlin
class NotesFile private constructor() {
    val entries = mutableListOf("Initial note")

    companion object {
        private var notesFile: NotesFile? = null

        fun getFile(): NotesFile =
            synchronized(this) {
                notesFile ?: NotesFile().also { notesFile = it }
            }
    }
}
```

#### Usage
```kotlin
val accessPoint1 = NotesFile.getFile()
trace("AP1 - ${accessPoint1.entries.joinToString()}")
val accessPoint2 = NotesFile.getFile()

accessPoint2.entries.add("Added from second access point")
trace("AP2 - ${accessPoint2.entries.joinToString()}")
```

#### Trace
```
AP1 - Initial note
AP2 - Initial note, Added from second access point
```


Structural
==========
>In software engineering, structural design patterns are design patterns that ease the design by identifying a simple way to realize relationships between entities.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Structural_pattern)


[Adapter](/src/main/kotlin/structural/adapter/Adapter.kt)
-----------------
Adapter is a structural design pattern that allows objects with incompatible interfaces to collaborate.

#### Example
```kotlin
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

class EuroSocket(
    private val euroPlug: EuroPlug,
) {
    fun connect() = """
        My plug has ${euroPlug.poles}.
        Fortunately, I didn't forget the adapter!
    """.trimIndent()
}
```

#### Usage
```kotlin
val myEuroPlugAdapter = EuroPlugAdapter()
val euroSocket = EuroSocket(euroPlug = myEuroPlugAdapter)
trace(euroSocket.connect())
```

#### Trace
```
My plug has two round poles.
Fortunately, I didn't forget the adapter!
```


[Bridge](/src/main/kotlin/structural/bridge/Bridge.kt)
-----------------
Bridge is a structural design pattern that lets you split a large class or a set of closely related classes into two separate hierarchies (abstraction and implementation) which can be developed independently of each other.

#### Example
```kotlin
open class FreeAudioPlayer(
    private val outputDevice: OutputDevice,
) {
    fun setVolume(value: Int): String = outputDevice.setVolume(value)
}

class AdvancedAudioPlayer(
    private val outputDevice: OutputDevice,
) : FreeAudioPlayer(outputDevice) {
    fun setBass(value: Int): String = outputDevice.setBass(value)
}

interface OutputDevice {
    fun setVolume(value: Int): String
    fun setBass(value: Int): String
}

class Headphones : OutputDevice {
    override fun setVolume(value: Int) = "Headphones' volume is set to $value"
    override fun setBass(value: Int) = "Headphones' bass is set to $value"
}

class Speakers : OutputDevice {
    override fun setVolume(value: Int) = "Speakers' volume is set to $value"
    override fun setBass(value: Int) = "Speakers' bass is set to $value"
}
```

#### Usage
```kotlin
val speakers = Speakers()
val freePlayer = FreeAudioPlayer(speakers)
trace(freePlayer.setVolume(10))

val headphones = Headphones()
val advancedPlayer = AdvancedAudioPlayer(headphones)
trace(advancedPlayer.setBass(24))
```

#### Trace
```
Speakers' volume is set to 10
Headphones' bass is set to 24
```


[Composite](/src/main/kotlin/structural/composite/Composite.kt)
-----------------
Composite is a structural design pattern that lets you compose objects into tree structures and then work with these structures as if they were individual objects.

#### Example
```kotlin
interface DirectoryComponent {
    fun getSizeKb(): Int
}

class File(
    val name: String,
    val content: String,
) : DirectoryComponent {

    override fun getSizeKb() = (content.count { it == ' ' } + 1) * 10

    override fun toString() = "$name file"
}

class Folder(
    val name: String,
) : DirectoryComponent {

    private val content = mutableListOf<DirectoryComponent>()

    fun add(component: DirectoryComponent) =
        content.add(component)

    fun remove(component: DirectoryComponent) =
        content.remove(component)

    fun getContent(): String = content.joinToString()

    override fun getSizeKb() = content.fold(initial = 0) { acc, directoryComponent ->
        acc + directoryComponent.getSizeKb()
    }

    override fun toString() = "$name folder"
}
```

#### Usage
```kotlin
val rootFolder = Folder(name = "Root")
val innerFolder = Folder(name = "Inner")
val clientsFile = File(name = "Clients.txt", content = "Larry, Tyson")
val employeesFile = File(name = "Employees.txt", content = "Mr.Bean")

rootFolder.add(innerFolder)
rootFolder.add(clientsFile)
innerFolder.add(employeesFile)

trace("$rootFolder (${rootFolder.getSizeKb()}Kb): ${rootFolder.getContent()}")
trace("$clientsFile: ${clientsFile.content}")
trace("$innerFolder (${innerFolder.getSizeKb()}Kb): ${innerFolder.getContent()}")
trace("$employeesFile: ${employeesFile.content}")
```

#### Trace
```
Root folder (30Kb): Inner folder, Clients.txt file
Clients.txt file: Larry, Tyson
Inner folder (10Kb): Employees.txt file
Employees.txt file: Mr.Bean
```


[Decorator](/src/main/kotlin/structural/decorator/Decorator.kt)
-----------------
Decorator is a structural design pattern that lets you attach new behaviors to objects by placing these objects inside special wrapper objects that contain the behaviors.

#### Example
```kotlin
interface Phone {
    val capacity: Int
    fun voiceSearch(): String
}

class BasicPhone : Phone {
    override val capacity = 256
    override fun voiceSearch() = "Basic voice search"
}

class PhoneSpecDecorator(phone: Phone) : Phone by phone {
    override val capacity = phone.capacity * 2
}

class PhoneSoftDecorator(phone: Phone) : Phone by phone {
    override fun voiceSearch() = "Enhanced voice search"
}

fun Phone.getInfo() = "Capacity - ${capacity}, ${voiceSearch()}"
```

#### Usage
```kotlin
val lowPricePhone = BasicPhone()
val mediumPricePhone = PhoneSpecDecorator(lowPricePhone)
val highPricePhone = PhoneSoftDecorator(mediumPricePhone)

trace("Low price phone: ${lowPricePhone.getInfo()}")
trace("Medium price phone: ${mediumPricePhone.getInfo()}")
trace("High price phone: ${highPricePhone.getInfo()}")
```

#### Trace
```
Low price phone: Capacity - 256, Basic voice search
Medium price phone: Capacity - 512, Basic voice search
High price phone: Capacity - 512, Enhanced voice search
```
