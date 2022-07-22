package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class login_page : AppCompatActivity() {

    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var Login: MaterialButton
    private lateinit var SignUp: MaterialButton

    private lateinit var mAuth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        mAuth = FirebaseAuth.getInstance()
        email = findViewById(R.id.et_Email)
        password = findViewById(R.id.et_password)
        Login= findViewById(R.id.bt_login)
        SignUp = findViewById(R.id.bt_signup)


        SignUp.setOnClickListener {
            val intent = Intent(this,signUp_Page::class.java)
            startActivity(intent)
        }

        Login.setOnClickListener {
            val email = email.text.toString()
            val pass = password.text.toString()

            login(email,pass);
        }
    }

    private fun login(email:String, pass:String){
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){task->
            if (task.isSuccessful){
                val intent = Intent(this@login_page,MainActivity::class.java)
                finish()
                startActivity(intent)
            }else{
                Toast.makeText(this@login_page,"User Does Not Exist", Toast.LENGTH_SHORT).show()
            }
        }
    }
}