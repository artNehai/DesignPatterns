package behavioral.cor

import util.trace

abstract class RequestHandler {
    var next: RequestHandler? = null
    abstract fun process(request: Request)
}

class Authenticator : RequestHandler() {
    override fun process(request: Request) {
        if (request.credentials == "J.Bond - MySolidPassword") {
            trace("Greetings, Mr.Bond. What can I do for you?")
            request.token = "007"
        } else throw Exception("Who the hell are you?")

        next?.process(request)
    }
}

class Authorizer : RequestHandler() {
    override fun process(request: Request) {
        if (request.token == "007") {
            trace("Here is the requested data")
        } else throw Exception("No data for you")

        next?.process(request)
    }
}

class Sanitizer : RequestHandler() {
    override fun process(request: Request) {
        trace("Not injecting anything, are you?")
        next?.process(request)
    }
}

data class Request(
    val credentials: String,
    var token: String? = null,
)

fun main() {
    val request = Request("J.Bond - MySolidPassword")
    val authenticator = Authenticator()
    val authorizer = Authorizer()
    authenticator.next = authorizer

    // Later, a new feature is introduced
    val sanitizer = Sanitizer()
    sanitizer.next = authenticator
    sanitizer.process(request)

    trace eq """
        Not injecting anything, are you?
        Greetings, Mr.Bond. What can I do for you?
        Here is the requested data
    """
}