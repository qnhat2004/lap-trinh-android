package com.example.bai4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextUrl: EditText
    private lateinit var buttonDownload: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Liên kết với các thành phần giao diện
        editTextUrl = findViewById(R.id.editTextUrl)
        buttonDownload = findViewById(R.id.buttonDownload)
        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)

        // Xử lý sự kiện nhấn nút
        buttonDownload.setOnClickListener {
            val url = editTextUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    val downloadTask = DownloadImageTask(progressBar, imageView)
                    downloadTask.execute(url)
                } else {
                    Toast.makeText(this, "URL phải bắt đầu bằng http:// hoặc https://", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Vui lòng nhập URL", Toast.LENGTH_SHORT).show()
            }
        }
    }
}