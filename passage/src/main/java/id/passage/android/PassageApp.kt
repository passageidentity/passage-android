package id.passage.android

import id.passage.android.api.AppsAPI
import id.passage.android.api.UsersAPI
import id.passage.android.exceptions.AppInfoException
import id.passage.android.exceptions.CreateUserException
import id.passage.android.exceptions.UserExistsException
import id.passage.android.model.CreateUserParams
import okhttp3.OkHttpClient

class PassageApp(
    private val passageClient: OkHttpClient,
) {
    /**
     * App Info
     *
     * Get information about an application.
     * @return PassageAppInfo
     * @throws AppInfoException
     */
    suspend fun info(): PassageAppInfo {
        val appsAPI = AppsAPI(Passage.BASE_PATH, passageClient)
        return try {
            appsAPI.getApp(Passage.appId)
        } catch (e: Exception) {
            throw AppInfoException.convert(e)
        }.app
    }

    /**
     * Identifier Exists
     *
     * Checks if the identifier provided exists for the application. This method should be used to
     * determine whether to register or log in a user. This method also checks that the app supports
     * the identifier types (e.g., it will throw an error if a phone number is supplied to an app
     * that only supports emails as an identifier).
     * @return PublicUserInfo?
     */
    suspend fun userExists(identifier: String): PublicUserInfo? {
        val usersAPI = UsersAPI()
        return try {
            usersAPI.checkUserIdentifier(Passage.appId, identifier).user
        } catch (e: Exception) {
            throw UserExistsException.convert(e)
        }
    }

    /**
     * Create User
     *
     * Creates a new user with the provided identifier and optional metadata. This method should be used to register a new user.
     * The `identifier` is required to uniquely identify the user (e.g., email or phone number).
     * The `userMetadata` can be used to provide additional information about the user.
     * @param identifier The identifier for the new user (e.g., email or phone number)
     * @param userMetadata Optional metadata associated with the user
     * @return PublicUserInfo? containing the created user's information, null if creation fails
     */
    suspend fun createUser(
        identifier: String,
        userMetadata: Any?,
    ): PublicUserInfo {
        val usersAPI = UsersAPI()
        return try {
            usersAPI.createUser(Passage.appId, CreateUserParams(identifier, userMetadata)).user ?: throw Exception("User is null")
        } catch (e: Exception) {
            throw CreateUserException.convert(e)
        }
    }
}
