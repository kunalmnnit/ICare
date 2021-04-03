package com.kunal.icare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kunal.icare.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.signUp.setOnClickListener {
            register()
        }
        binding.login.setOnClickListener {
            goToLogin()
        }
    }

    private fun register() {
        if(binding.email.text.isEmpty() || binding.Password.text.isEmpty() || binding.confirmPassword.text.isEmpty()) {
            Toast.makeText(this@SignUpActivity, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            return;
        }
        if(binding.Password.text.toString().equals(binding.confirmPassword.text.toString())==false) {
            Toast.makeText(this@SignUpActivity,"Password mismatch",Toast.LENGTH_SHORT).show()
            return;
        }

        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(binding.email.text.toString(), binding.Password.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this@SignUpActivity, Scanner::class.java))
                }
                else
                    Toast.makeText(this@SignUpActivity, task.result.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun goToLogin() {
        startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        goToLogin()
    }
}