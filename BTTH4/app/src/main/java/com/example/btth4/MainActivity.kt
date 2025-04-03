package com.example.btth4

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var lvContact: ListView
    private var selectedContactId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnCreate = findViewById<Button>(R.id.btnCreate)
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        val btnShow = findViewById<Button>(R.id.btnShow)
        val btnEdit = findViewById<Button>(R.id.btnEdit)
        lvContact = findViewById(R.id.lvContact)

        dbHelper = DatabaseHelper(this)

        btnCreate.setOnClickListener {
            val username = etUsername.text.toString()
            val phone = etPhone.text.toString()
            if (username.isNotEmpty() && phone.isNotEmpty()) {
                val id = dbHelper.addContact(username, phone)
                if (id != -1L) {
                    Toast.makeText(this, "Đã lưu dữ liệu", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Lỗi khi lưu dữ liệu", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            selectedContactId?.let {
                dbHelper.deleteContact(it)
                Toast.makeText(this, "Đã xóa dữ liệu", Toast.LENGTH_SHORT).show()
                selectedContactId = null
                showContacts()
            } ?: Toast.makeText(this, "Vui lòng chọn một liên hệ để xóa", Toast.LENGTH_SHORT).show()
        }

        btnEdit.setOnClickListener {
            selectedContactId?.let {
                val username = etUsername.text.toString()
                val phone = etPhone.text.toString()
                if (username.isNotEmpty() && phone.isNotEmpty()) {
                    dbHelper.updateContact(it, username, phone)
                    Toast.makeText(this, "Đã cập nhật dữ liệu", Toast.LENGTH_SHORT).show()
                    selectedContactId = null
                    showContacts()
                } else {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            } ?: Toast.makeText(this, "Vui lòng chọn một liên hệ để sửa", Toast.LENGTH_SHORT).show()
        }

        btnShow.setOnClickListener {
            showContacts()
        }

        lvContact.setOnItemClickListener { _, _, position, _ ->
            val contacts = dbHelper.getAllContacts()
            selectedContactId = contacts[position].id
            etUsername.setText(contacts[position].username)
            etPhone.setText(contacts[position].phone)
        }
    }

    private fun showContacts() {
        val contacts = dbHelper.getAllContacts()
        val contactList = contacts.map { "${it.id}: ${it.username} - ${it.phone}" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactList)
        lvContact.adapter = adapter
    }
}