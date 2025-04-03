package com.example.firebaseauthdemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo Firebase Auth và Realtime Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Khai báo các view
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnShowData = findViewById<Button>(R.id.btnShowData)
        val tvData = findViewById<TextView>(R.id.tvData)

        // Xử lý nút Đăng ký
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener   // Thoát khỏi hàm
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Đăng ký thành công
                        val user = auth.currentUser
                        Toast.makeText(
                            this, "Đăng ký thành công: ${user?.email}", Toast.LENGTH_SHORT
                        ).show()

                        // Lưu thông tin người dùng vào Realtime Database
                        val userId = user?.uid
                        val userData =
                            mapOf("email" to email, "registeredAt" to System.currentTimeMillis())
                        if (userId != null) {
                            database.child("users").child(userId).setValue(userData)    // ứng dụng được lưu tại node users/userId
                        }
                    } else {
                        // Đăng ký thất bại
                        Toast.makeText(
                            this, "Đăng ký thất bại: ${task.exception?.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // Xử lý nút Đăng nhập
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập email và mật khẩu!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Đăng nhập thành công
                        val user = auth.currentUser
                        Toast.makeText(
                            this, "Đăng nhập thành công: ${user?.email}", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Đăng nhập thất bại
                        Toast.makeText(
                            this,
                            "Đăng nhập thất bại: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // Xử lý nút Hiển thị dữ liệu
        btnShowData.setOnClickListener {
            val user = auth.currentUser
            if (user == null) {
                Toast.makeText(this, "Vui lòng đăng nhập trước!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = user.uid
            database.child("users").child(userId)   // truy cập vào node users/userId
                .addListenerForSingleValueEvent(object : ValueEventListener {   // lắng nghe sự kiện một lần, object là 1 đối tượng ẩn danh của interface ValueEventListener (nó là 1 class nhưng không có tên)
                    override fun onDataChange(snapshot: DataSnapshot) {     // snapshot chứa dữ liệu trả về từ Realtime Database
                        if (snapshot.exists()) {
                            val userData = snapshot.value as Map<*, *>      // snapshot.value trả về kiểu Any, ép kiểu về Map<*, *> để lấy dữ liệu
                            val email = userData["email"] as String?    // lấy dữ liệu từ Map
                            val registeredAt = userData["registeredAt"] as Long?    // lấy dữ liệu từ Map
                            tvData.text = "Email: $email\nThời gian đăng ký: $registeredAt"     //
                        } else {
                            tvData.text = "Không có dữ liệu!"
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@MainActivity, "Lỗi: ${error.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}