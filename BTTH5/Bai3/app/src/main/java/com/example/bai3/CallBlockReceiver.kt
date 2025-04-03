package com.example.bai3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class CallBlockerReceiver : BroadcastReceiver() {

    companion object {
        // Danh sách số bị chặn (lưu tạm trong bộ nhớ, có thể thay bằng SharedPreferences hoặc DB)
        val blockedNumbers = mutableListOf<String>()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.PHONE_STATE") {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            Log.d("CallBlocker", "State: $state, Number: $incomingNumber")

            if (state == TelephonyManager.EXTRA_STATE_RINGING && incomingNumber != null) {
                if (blockedNumbers.contains(incomingNumber)) {
                    Log.d("CallBlocker", "Blocked call from: $incomingNumber")
                    Toast.makeText(context, "Chặn cuộc gọi từ: $incomingNumber", Toast.LENGTH_SHORT).show()
                    // Thử chặn cuộc gọi (xem Bước 5)
                    blockCall(context, incomingNumber)
                } else {
                    Log.d("CallBlocker", "Incoming call allowed: $incomingNumber")
                }
            }
        }
    }

    private fun blockCall(context: Context?, incomingNumber: String) {
        // Ghi log và thông báo, vì chặn trực tiếp bị hạn chế
        // Có thể thay bằng phương pháp nâng cao ở Bước 5
        Log.d("CallBlocker", "Attempting to block call from: $incomingNumber")
    }
}