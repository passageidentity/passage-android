package id.passage.android.utils

import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

internal class StringUtils {
    companion object {
        private const val SECRET_STRING_LENGTH = 32

        fun getRandomString(): String {
            val digits = '0'..'9'
            val upperCaseLetters = 'A'..'Z'
            val lowerCaseLetters = 'a'..'z'
            val characters =
                (
                    digits +
                        upperCaseLetters +
                        lowerCaseLetters
                ).joinToString("")
            val random = SecureRandom()
            val stringBuilder = StringBuilder(SECRET_STRING_LENGTH)
            for (i in 0 until SECRET_STRING_LENGTH) {
                val randomIndex = random.nextInt(characters.length)
                stringBuilder.append(characters[randomIndex])
            }
            return stringBuilder.toString()
        }

        fun sha256Hash(randomString: String): String {
            val bytes = randomString.toByteArray()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(bytes)
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest)
        }
    }
}
