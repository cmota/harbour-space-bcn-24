package com.harbourspace.unsplash

import com.harbourspace.unsplash.utils.isValidEmail
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue("name@email.com".isValidEmail())
    }

    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsFalse() {
        assertFalse("nameemail.com".isValidEmail())
    }
}
