@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

/**
 * Thrown when there's an error logging in a user, typically an issue with Passage app setup.
 *
 * @see LoginInvalidAppException
 * @see LoginNoExistingUserException
 * @see LoginNoFallbackException
 */
public open class LoginException(message: String): PassageException(message)

/**
 * Thrown when there's an issue with the Passage app, typically indicating "passage_app_id" has not
 * been set in your strings.xml file.
 */
public class LoginInvalidAppException(message: String = "Invalid Passage app."): LoginException(message)

/**
 * Thrown when login attempt failed because no user exists with provided identifier.
 */
public class LoginNoExistingUserException(message: String = "User with this identifier does not exist."): LoginException(message)

/**
 * Thrown when attempting to use a fallback login method but none has been set in Passage app.
 */
public class LoginNoFallbackException(message: String = "Passage app has no fallback set."): LoginException(message)
