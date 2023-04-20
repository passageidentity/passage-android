package id.passage.android

import id.passage.client.infrastructure.ClientException
import id.passage.client.infrastructure.ServerException

class PassageException(message: String): RuntimeException(message)

class PassageWebAuthnException(message: String): Exception(message) {
    companion object {
        const val CHALLENGE_MISSING = "WebAuthn credential assertion challenge missing."
        const val PARSING_FAILED = "WebAuthn credential assertion challenge parsing failed."
    }
}

class PassageCredentialException(message: String): Exception(message) {
    companion object {
        const val PARSING_FAILED = "Credential Manager handshake response parsing failed."
    }
}

typealias PassageServerException = ServerException

typealias PassageClientException = ClientException
