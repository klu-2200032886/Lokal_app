package com.example.otpauthapp.data

class OtpManager {

    private var currentOtp: String? = null
    private var otpTime: Long = 0L
    private var attempts = 0

    private val expiryTime = 60_000 // 60 seconds
    private val maxAttempts = 3

    fun generateOtp(): String {
        val otp = (100000..999999).random().toString()
        currentOtp = otp
        otpTime = System.currentTimeMillis()
        attempts = 0 // reset attempts
        return otp
    }

    fun verifyOtp(inputOtp: String): VerifyResult {
        val currentTime = System.currentTimeMillis()

        if (currentOtp == null) {
            return VerifyResult.Expired
        }

        if (currentTime - otpTime > expiryTime) {
            currentOtp = null
            return VerifyResult.Expired
        }

        if (attempts >= maxAttempts) {
            currentOtp = null
            return VerifyResult.MaxAttemptsExceeded
        }

        attempts++

        return if (inputOtp == currentOtp) {
            VerifyResult.Success
        } else {
            if (attempts >= maxAttempts) {
                currentOtp = null
                VerifyResult.MaxAttemptsExceeded
            } else {
                VerifyResult.Wrong
            }
        }
    }

    sealed class VerifyResult {
        object Success : VerifyResult()
        object Wrong : VerifyResult()
        object Expired : VerifyResult()
        object MaxAttemptsExceeded : VerifyResult()
    }
}
