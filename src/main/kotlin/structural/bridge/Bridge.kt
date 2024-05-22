package structural.bridge

import util.trace

// Abstraction
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

// Implementation
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

fun main() {
    val speakers = Speakers()
    val freePlayer = FreeAudioPlayer(speakers)
    trace(freePlayer.setVolume(10))

    val headphones = Headphones()
    val advancedPlayer = AdvancedAudioPlayer(headphones)
    trace(advancedPlayer.setBass(24))

    trace eq """
        Speakers' volume is set to 10
        Headphones' bass is set to 24
    """
}