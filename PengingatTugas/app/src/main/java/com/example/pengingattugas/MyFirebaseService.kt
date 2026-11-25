package com.example.pengingattugas

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        // Kirim token ini ke server Anda, lalu simpan di database
        Log.d("FCM", "Token baru: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        // Studi kasus memakai data message: judul, isi, dan tugas_id
        val judul = message.data["judul"] ?: "Pengingat"
        val isi = message.data["isi"] ?: ""
        val tugasId = message.data["tugas_id"]
        tampilkanNotifikasi(judul, isi, tugasId)
    }

    private fun tampilkanNotifikasi(judul: String, isi: String, tugasId: String?) {
        val channelId = "pengingat_tugas"
        val manager = getSystemService(NotificationManager::class.java)

        // Channel wajib dibuat sejak Android 8.0 (API 26)
        if (manager.getNotificationChannel(channelId) == null) {
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    "Pengingat Tugas",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        // Saat diketuk, MainActivity terbuka dan menerima tugas_id
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("tugas_id", tugasId)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notifikasi = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifikasi)
            .setContentTitle(judul)
            .setContentText(isi)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notifikasi)
    }
}