package structural.proxy

import util.trace

interface ThirdPartyService {
    fun doSomeWork(): String
}

class HeavyweightService : ThirdPartyService {
    override fun doSomeWork() = "Some work"
}

class VirtualServiceProxy : ThirdPartyService {

    private val service = lazy { HeavyweightService() }

    override fun doSomeWork() = service.value.doSomeWork()
}

fun main() {
    val service: ThirdPartyService = VirtualServiceProxy()
    trace(service.doSomeWork())

    trace eq "Some work"
}