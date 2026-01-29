package com.example.otpauthapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.otpauthapp.data.OtpManager
import com.example.otpauthapp.ui.LoginScreen
import com.example.otpauthapp.ui.OtpScreen
import com.example.otpauthapp.ui.SessionScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            val otpManager = remember { OtpManager() }
            var screen by remember { mutableStateOf("LOGIN") }

            when (screen) {

                // LOGIN SCREEN
                "LOGIN" -> LoginScreen(
                    onSendOtpClick = {
                        otpManager.generateOtp()
                        screen = "OTP"
                    }
                )

                // OTP SCREEN
                "OTP" -> OtpScreen(
                    onVerifyOtp = { enteredOtp ->
                        val result = otpManager.verifyOtp(enteredOtp)
                        if (result is OtpManager.VerifyResult.Success) {
                            screen = "SESSION"
                        }
                        result
                    },
                    onOtpExpiredOrBlocked = {
                        screen = "LOGIN"
                    }
                )

                // SESSION SCREEN
                "SESSION" -> SessionScreen(
                    onLogout = {
                        screen = "LOGIN"
                    }
                )
            }
        }
    }
}
