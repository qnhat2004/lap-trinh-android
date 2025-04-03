package com.example.bai4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import java.net.HttpURLConnection
import java.net.URL

class DownloadImageTask(
    private val progressBar: ProgressBar,
    private val imageView: ImageView
) : AsyncTask<String, Int, Bitmap?>() {

    override fun onPreExecute() {
        // Trước khi tải: hiển thị ProgressBar
        progressBar.visibility = View.VISIBLE
        imageView.setImageBitmap(null) // Xóa ảnh cũ nếu có
    }

    override fun doInBackground(vararg urls: String): Bitmap? {
        val urlString = urls[0]
        var bitmap: Bitmap? = null

        try {
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            val inputStream = connection.inputStream
            val totalSize = connection.contentLength // Tổng kích thước file (byte)
            val buffer = ByteArray(1024)
            var downloadedSize = 0
            val byteArrayOutputStream = java.io.ByteArrayOutputStream()

            // Đọc dữ liệu theo từng phần
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                downloadedSize += bytesRead
                byteArrayOutputStream.write(buffer, 0, bytesRead)

                // Cập nhật tiến trình (nếu biết kích thước file)
                if (totalSize > 0) {
                    val progress = (downloadedSize * 100 / totalSize)
                    publishProgress(progress)
                }
            }

            // Chuyển dữ liệu thành Bitmap
            val imageBytes = byteArrayOutputStream.toByteArray()
            bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

            inputStream.close()
            byteArrayOutputStream.close()
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }

    override fun onProgressUpdate(vararg values: Int?) {
        // Cập nhật ProgressBar
        progressBar.progress = values[0] ?: 0
    }

    override fun onPostExecute(result: Bitmap?) {
        // Sau khi tải xong: ẩn ProgressBar và hiển thị ảnh
        progressBar.visibility = View.GONE
        if (result != null) {
            imageView.setImageBitmap(result)
        } else {
            imageView.setImageResource(android.R.drawable.ic_dialog_alert) // Ảnh lỗi nếu tải thất bại
        }
    }
}