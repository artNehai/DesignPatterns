package structural.decorator

import util.trace

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

fun main() {
    val lowPricePhone = BasicPhone()
    val mediumPricePhone = PhoneSpecDecorator(lowPricePhone)
    val highPricePhone = PhoneSoftDecorator(mediumPricePhone)

    trace("Low price phone: ${lowPricePhone.getInfo()}")
    trace("Medium price phone: ${mediumPricePhone.getInfo()}")
    trace("High price phone: ${highPricePhone.getInfo()}")

    trace eq """
        Low price phone: Capacity - 256, Basic voice search
        Medium price phone: Capacity - 512, Basic voice search
        High price phone: Capacity - 512, Enhanced voice search
    """
}