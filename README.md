# Design Patterns In Kotlin
Inspired by [design-patterns-kotlin](https://github.com/mmolosay/design-patterns-kotlin "design-patterns-kotlin") by [mmolosay](https://github.com/mmolosay "mmolosay"), 
be sure to leave him a star.

## Table of Contents

* [Creational Patterns](#creational)
	* [Factory Method](#factory-method)
	* [Abstract Factory](#abstract-factory)
	* [Builder](#builder)


Creational
==========
> In software engineering, creational design patterns are design patterns that deal with object creation mechanisms, trying to create objects in a manner suitable to the
> situation.The basic form of object creation could result in design problems or added complexity to the design. Creational design patterns solve this problem by somehow
> controlling this object creation.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Creational_pattern)


[Factory Method](/src/main/kotlin/creational/factory_method)
-----------------

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
