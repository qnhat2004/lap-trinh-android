package com.example.missescallresponse

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_PERMISSIONS = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Kiểm tra và yêu cầu quyền
        requestPermissions()
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS
        )

        if (!hasPermissions(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS)
        } else {
            Toast.makeText(this, "Quyền đã được cấp", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Quyền được cấp", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Vui lòng cấp quyền để ứng dụng hoạt động", Toast.LENGTH_LONG).show()
            }
        }
    }
}