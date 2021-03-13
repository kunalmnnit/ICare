 package com.kunal.icare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kunal.icare.databinding.ActivityLoginBinding

 class LoginActivity : AppCompatActivity() {
     private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        val auth = Firebase.auth
        if(auth.currentUser!=null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
        else
            setContentView(view)
        binding.signUp.setOnClickListener {
            val i = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.signIn.setOnClickListener {
            login()
        }
    }

     private fun login() {
         val auth = Firebase.auth
         auth.signInWithEmailAndPassword(binding.emailAddress.text.toString(), binding.password.text.toString()).addOnCompleteListener { task ->
             if (task.isSuccessful) {
                 Toast.makeText(this@LoginActivity, "User Successfully logged in", Toast.LENGTH_SHORT).show()
                 val user = auth.currentUser!!.uid
                 startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                 finish()
             } else {
                 Toast.makeText(this@LoginActivity, "Wrong Email Address or Password", Toast.LENGTH_SHORT).show()
             }
         }
     }

     override fun onBackPressed() {
         finish()
     }
}
