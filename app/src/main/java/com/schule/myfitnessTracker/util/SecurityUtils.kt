package com.schule.myfitnessTracker.util

import java.security.MessageDigest

object SecurityUtils {
    fun hashPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

    fun isValidPassword(password: String): Boolean {
        // Mind. 8 Zeichen, 1 Zahl, 1 Sonderzeichen
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$"
        return password.matches(passwordPattern.toRegex())
    }
}
