@file:Suppress("RedundantVisibilityModifier")

package id.passage.android.exceptions

/**
 * Thrown when there's an error registering a user, typically an issue with Passage app setup.
 *
 * @see RegisterUserExistsException
 * @see RegisterInvalidAppException
 * @see RegisterPublicDisabledException
 * @see RegisterNoFallbackException
 */
public open class RegisterException(message: String) : PassageException(message)

/**
 * Thrown when a user with provided identifier already exists.
 */
public class RegisterUserExistsException(message: String = "User already exists.") : RegisterException(message)

/**
 * Thrown when there's an issue with the Passage app, typically indicating "passage_app_id" has not
 * been set in your strings.xml file.
 */
public class RegisterInvalidAppException(message: String = "Invalid Passage app.") : RegisterException(message)

/**
 * Thrown when your Passage app has "public_signup" set to false.
 */
public class RegisterPublicDisabledException(message: String = "Public registration disabled for this app.") : RegisterException(message)

/**
 * Thrown when attempting to use a fallback registration method but none has been set in Passage app.
 */
public class RegisterNoFallbackException(message: String = "Passage app has no fallback set.") : RegisterException(message)
