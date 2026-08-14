package work.jsfr.uuidgenerator.utils

import org.junit.Assert.*
import org.junit.Test

class UuidEngineTest {

    @Test
    fun testGenerateV4_lowercaseWithHyphens() {
        val uuid = UuidEngine.generateV4(isUppercase = false, removeHyphens = false)
        // Length must be 36
        assertEquals(36, uuid.length)
        // Match UUID v4 pattern (lowercase)
        val regex = Regex("^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
        assertTrue("UUID v4 pattern mismatch: $uuid", regex.matches(uuid))
    }

    @Test
    fun testGenerateV4_uppercaseWithHyphens() {
        val uuid = UuidEngine.generateV4(isUppercase = true, removeHyphens = false)
        assertEquals(36, uuid.length)
        val regex = Regex("^[0-9A-F]{8}-[0-9A-F]{4}-4[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$")
        assertTrue("UUID v4 pattern mismatch: $uuid", regex.matches(uuid))
    }

    @Test
    fun testGenerateV4_lowercaseNoHyphens() {
        val uuid = UuidEngine.generateV4(isUppercase = false, removeHyphens = true)
        assertEquals(32, uuid.length)
        val regex = Regex("^[0-9a-f]{8}[0-9a-f]{4}4[0-9a-f]{3}[89ab][0-9a-f]{3}[0-9a-f]{12}$")
        assertTrue("UUID v4 pattern mismatch: $uuid", regex.matches(uuid))
    }

    @Test
    fun testGenerateV4_uppercaseNoHyphens() {
        val uuid = UuidEngine.generateV4(isUppercase = true, removeHyphens = true)
        assertEquals(32, uuid.length)
        val regex = Regex("^[0-9A-F]{8}[0-9A-F]{4}4[0-9A-F]{3}[89AB][0-9A-F]{3}[0-9A-F]{12}$")
        assertTrue("UUID v4 pattern mismatch: $uuid", regex.matches(uuid))
    }

    @Test
    fun testGenerateV7_lowercaseWithHyphens() {
        val uuid = UuidEngine.generateV7(isUppercase = false, removeHyphens = false)
        assertEquals(36, uuid.length)
        val regex = Regex("^[0-9a-f]{8}-[0-9a-f]{4}-7[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
        assertTrue("UUID v7 pattern mismatch: $uuid", regex.matches(uuid))
    }

    @Test
    fun testGenerateV7_uppercaseWithHyphens() {
        val uuid = UuidEngine.generateV7(isUppercase = true, removeHyphens = false)
        assertEquals(36, uuid.length)
        val regex = Regex("^[0-9A-F]{8}-[0-9A-F]{4}-7[0-9A-F]{3}-[89AB][0-9A-F]{3}-[0-9A-F]{12}$")
        assertTrue("UUID v7 pattern mismatch: $uuid", regex.matches(uuid))
    }

    @Test
    fun testGenerateV7_lowercaseNoHyphens() {
        val uuid = UuidEngine.generateV7(isUppercase = false, removeHyphens = true)
        assertEquals(32, uuid.length)
        val regex = Regex("^[0-9a-f]{8}[0-9a-f]{4}7[0-9a-f]{3}[89ab][0-9a-f]{3}[0-9a-f]{12}$")
        assertTrue("UUID v7 pattern mismatch: $uuid", regex.matches(uuid))
    }

    @Test
    fun testGenerateV7_uppercaseNoHyphens() {
        val uuid = UuidEngine.generateV7(isUppercase = true, removeHyphens = true)
        assertEquals(32, uuid.length)
        val regex = Regex("^[0-9A-F]{8}[0-9A-F]{4}7[0-9A-F]{3}[89AB][0-9A-F]{3}[0-9A-F]{12}$")
        assertTrue("UUID v7 pattern mismatch: $uuid", regex.matches(uuid))
    }

    @Test
    fun testGenerateBatch_v4() {
        val count = 10
        val batch = UuidEngine.generateBatch(count = count, version = "V4", isUppercase = false, removeHyphens = false)
        assertEquals(count, batch.size)
        val regex = Regex("^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$")
        batch.forEach { uuid ->
            assertTrue("Batch UUID v4 pattern mismatch: $uuid", regex.matches(uuid))
        }
    }

    @Test
    fun testGenerateBatch_v7() {
        val count = 5
        val batch = UuidEngine.generateBatch(count = count, version = "V7", isUppercase = true, removeHyphens = true)
        assertEquals(count, batch.size)
        val regex = Regex("^[0-9A-F]{8}[0-9A-F]{4}7[0-9A-F]{3}[89AB][0-9A-F]{3}[0-9A-F]{12}$")
        batch.forEach { uuid ->
            assertTrue("Batch UUID v7 pattern mismatch: $uuid", regex.matches(uuid))
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGenerateBatch_invalidVersion() {
        UuidEngine.generateBatch(count = 5, version = "INVALID", isUppercase = false, removeHyphens = false)
    }
}
