package com.example.otpauthapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.otpauthapp.data.OtpManager

@Composable
fun OtpScreen(
    onVerifyOtp: (String) -> OtpManager.VerifyResult,
    onOtpExpiredOrBlocked: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Enter OTP")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = otp,
            onValueChange = {
                otp = it
                message = null
            },
            label = { Text("OTP") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when (onVerifyOtp(otp)) {
                    OtpManager.VerifyResult.Success -> {
                        // Navigation handled in MainActivity
                    }
                    OtpManager.VerifyResult.Wrong -> {
                        message = "Wrong OTP. Try again."
                    }
                    OtpManager.VerifyResult.Expired -> {
                        message = "OTP expired"
                        onOtpExpiredOrBlocked()
                    }
                    OtpManager.VerifyResult.MaxAttemptsExceeded -> {
                        message = "Maximum attempts exceeded"
                        onOtpExpiredOrBlocked()
                    }
                }
            }
        ) {
            Text("Verify OTP")
        }

        message?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = Color.Red)
        }
    }
}
