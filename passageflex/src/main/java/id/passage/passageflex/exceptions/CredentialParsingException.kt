package id.passage.passageflex.exceptions

/**
 * Thrown when parsing of WebAuthn credentials fails.
 */
public open class CredentialParsingException(message: String) : PassageFlexException(message) {
    internal companion object {
        const val CHALLENGE_MISSING = "WebAuthn credential assertion challenge missing."
        const val CHALLENGE_PARSING_FAILED = "WebAuthn credential assertion challenge parsing failed."
        const val CREDENTIAL_PARSING_FAILED = "Credential Manager handshake response parsing failed."
    }
}
