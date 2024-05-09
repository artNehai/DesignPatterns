# Design Patterns In Kotlin
Inspired by [design-patterns-kotlin](https://github.com/mmolosay/design-patterns-kotlin "design-patterns-kotlin") by [mmolosay](https://github.com/mmolosay "mmolosay"), 
be sure to leave him a star in my stead.

## Table of Contents

* [Creational Patterns](#creational)
	* [Factory Method](#factory-method)
	* [Abstract Factory](#abstract-factory)

Creational
==========
> In software engineering, creational design patterns are design patterns that deal with object creation mechanisms, trying to create objects in a manner suitable to the
> situation.The basic form of object creation could result in design problems or added complexity to the design. Creational design patterns solve this problem by somehow
> controlling this object creation.
>
>**Source:** [wikipedia.org](http://en.wikipedia.org/wiki/Creational_pattern)

[Factory Method](/src/main/kotlin/creational/factory_method)
-----------------

#### Problem
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
