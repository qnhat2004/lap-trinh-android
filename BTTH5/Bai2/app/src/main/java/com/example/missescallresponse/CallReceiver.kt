package com.example.missescallresponse

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {

    companion object {
        private var lastState: String? = null
        private var incomingNumber: String? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.PHONE_STATE") {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            when (state) {
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    // Cuộc gọi đang đổ chuông
                    incomingNumber = number
                    lastState = state
                    Log.d("CallReceiver", "Ringing from: $number")
                }
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    // Cuộc gọi đã kết thúc (nhỡ hoặc từ chối)
                    if (lastState == TelephonyManager.EXTRA_STATE_RINGING && incomingNumber != null) {
                        // Đây là cuộc gọi nhỡ
                        sendSMS(context, incomingNumber)
                        incomingNumber = null // Reset sau khi gửi
                    }
                    lastState = state
                    Log.d("CallReceiver", "Idle")
                }
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    // Cuộc gọi được chấp nhận (người dùng nghe máy)
                    lastState = state
                    incomingNumber = null // Reset nếu nghe máy
                    Log.d("CallReceiver", "Offhook")
                }
            }
        }
    }

    private fun sendSMS(context: Context?, phoneNumber: String?) {
        try {
            val smsManager = SmsManager.getDefault()
            val message = "Xin lỗi, tôi đang bận. Tôi sẽ gọi lại cho bạn sau!"
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("CallReceiver", "SMS sent to $phoneNumber")
        } catch (e: Exception) {
            Log.e("CallReceiver", "Failed to send SMS: ${e.message}")
        }
    }
}