package com.android.fairybaked

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.fairybaked.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            handleLogin()
        }
    }

    private fun handleLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString()

        // Validasi
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        // Logika login
        if (username == "admin" && password == "12345") {
            // Jika login berhasil
            Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)

            // Tutup halaman login
            finish()
        } else {
            // Jika login gagal
            Toast.makeText(this, "Username atau Password Salah", Toast.LENGTH_SHORT).show()
        }
    }
}