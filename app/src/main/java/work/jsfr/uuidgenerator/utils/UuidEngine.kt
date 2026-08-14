package work.jsfr.uuidgenerator.utils

import java.security.SecureRandom
import java.util.UUID

object UuidEngine {

    private val secureRandom = SecureRandom()

    fun generateV4(isUppercase: Boolean, removeHyphens: Boolean): String {
        val uuid = UUID.randomUUID()
        return formatUuid(uuid.toString(), isUppercase, removeHyphens)
    }

    fun generateV7(isUppercase: Boolean, removeHyphens: Boolean): String {
        val timestamp = System.currentTimeMillis()
        val randA = secureRandom.nextInt(4096).toLong() and 0x0FFFL
        val msb = ((timestamp and 0xFFFFFFFFFFFFL) shl 16) or (7L shl 12) or randA
        
        val lsb = (secureRandom.nextLong() and 0x3FFFFFFFFFFFFFFFL) or (1L shl 63)
        val uuid = UUID(msb, lsb)
        return formatUuid(uuid.toString(), isUppercase, removeHyphens)
    }

    fun generateBatch(count: Int, version: String, isUppercase: Boolean, removeHyphens: Boolean): List<String> {
        if (count < 0) {
            throw IllegalArgumentException("Count must be non-negative")
        }
        return List(count) {
            when (version.uppercase()) {
                "V4" -> generateV4(isUppercase, removeHyphens)
                "V7" -> generateV7(isUppercase, removeHyphens)
                else -> throw IllegalArgumentException("Unsupported UUID version: $version")
            }
        }
    }

    private fun formatUuid(uuidStr: String, isUppercase: Boolean, removeHyphens: Boolean): String {
        var formatted = uuidStr
        if (removeHyphens) {
            formatted = formatted.replace("-", "")
        }
        return if (isUppercase) {
            formatted.uppercase()
        } else {
            formatted.lowercase()
        }
    }
}
