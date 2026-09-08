package com.example.helloworld

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button)

        // Tombol Simpan
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            Toast.makeText(
                this,
                "Simpan data berhasil",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Tombol Batal
        val btnBatal = findViewById<Button>(R.id.btnBatal)

        btnBatal.setOnClickListener {
            Toast.makeText(
                this,
                "Dibatalkan",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Dipanggil dari android:onClick="simpanData"
    fun simpanData(view: View) {
        Toast.makeText(
            this,
            "Simpan data berhasil",
            Toast.LENGTH_SHORT
        ).show()
    }
}