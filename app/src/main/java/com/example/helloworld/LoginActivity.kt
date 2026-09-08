package com.example.helloworld

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Cek field kosong
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Isi semua field",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Cek login
            if (email == "admin@mail.com" && password == "12345") {

                Toast.makeText(
                    this,
                    "Login berhasil!",
                    Toast.LENGTH_SHORT
                ).show()

                // Pindah ke MainActivity
                val intent = Intent(
                    this@LoginActivity,
                    MainActivity::class.java
                )

                startActivity(intent)

                // Hapus LoginActivity dari back stack
                finish()

            } else {

                Toast.makeText(
                    this,
                    "Email atau password salah",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}