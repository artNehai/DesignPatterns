package structural.facade

import util.trace

object ComplexNetworkApi {
    fun makeRequest(url: String, method: String) =
        """
                Request: url-$url, method-$method
                Result: content-secret_juice.png, status_code-200 
            """

    fun processRequestResult(result: String) =
        result.substringAfter("content-").substringBefore(",")
}

object Facade {
    fun obliviousPhotoRequest(url: String): String {
        val result = ComplexNetworkApi.makeRequest(url, "GET")
        return ComplexNetworkApi.processRequestResult(result)
    }
}

fun main() {
    val photo = Facade.obliviousPhotoRequest("www.photolab.com/secret_juice.png")
    trace(photo)

    trace eq """
        secret_juice.png
    """
}