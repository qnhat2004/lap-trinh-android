package com.example.bai3

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonAddNumber: Button
    private lateinit var listViewBlockedNumbers: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val REQUEST_PERMISSIONS = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        buttonAddNumber = findViewById(R.id.buttonAddNumber)
        listViewBlockedNumbers = findViewById(R.id.listViewBlockedNumbers)

        // Khởi tạo adapter cho ListView
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, CallBlockerReceiver.blockedNumbers)
        listViewBlockedNumbers.adapter = adapter

        // Thêm số vào danh sách chặn
        buttonAddNumber.setOnClickListener {
            val phoneNumber = editTextPhoneNumber.text.toString().trim()
            if (phoneNumber.isNotEmpty()) {
                if (!CallBlockerReceiver.blockedNumbers.contains(phoneNumber)) {
                    CallBlockerReceiver.blockedNumbers.add(phoneNumber)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this, "Đã thêm $phoneNumber vào danh sách chặn", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Số này đã có trong danh sách", Toast.LENGTH_SHORT).show()
                }
                editTextPhoneNumber.text.clear()
            } else {
                Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show()
            }
        }

        // Xin quyền
        requestPermissions()
    }

    private fun requestPermissions() {
        val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE)
        if (!hasPermissions(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS)
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
        if (requestCode == REQUEST_PERMISSIONS && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            Toast.makeText(this, "Quyền được cấp", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Vui lòng cấp quyền để ứng dụng hoạt động", Toast.LENGTH_LONG).show()
        }
    }
}