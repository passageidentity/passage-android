package id.passage.authentikit

public open class AuthentikitException(message: String) : RuntimeException(message)

public open class PasskeyException(message: String) : AuthentikitException(message)

public open class PasskeyEvaluationException(message: String) : AuthentikitException(message)