package com.example.pengingattugas

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    // Harus berupa properti kelas, bukan di dalam onCreate
    private val mintaIzin = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { diberikan ->
        if (!diberikan) {
            Toast.makeText(this, "Notifikasi dimatikan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Android 13 (API 33) ke atas wajib meminta izin notifikasi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mintaIzin.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // Ambil token perangkat dan cetak ke Logcat
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM", "Token: ${task.result}")
            }
        }

        // Tampilkan tugas dari notifikasi yang diketuk di TextView txtStatus
        val txtStatus = findViewById<TextView>(R.id.txtStatus)
        val tugasId = intent.getStringExtra("tugas_id")
        txtStatus.text = if (tugasId != null) "Buka tugas $tugasId" else "Menunggu pengingat tugas"
    }
}