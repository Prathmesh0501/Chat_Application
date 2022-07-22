package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signUp_Page : AppCompatActivity() {

    private lateinit var email: TextInputEditText
    private lateinit var name: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var SignUp: MaterialButton

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_page)

        mAuth = FirebaseAuth.getInstance()
        name = findViewById(R.id.et_Name)
        email = findViewById(R.id.et_Email)
        password = findViewById(R.id.et_password)

        SignUp = findViewById(R.id.bt_signup)

        SignUp.setOnClickListener {
            val name = name.text.toString()
            val email = email.text.toString()
            val pass = password.text.toString()

            SignUp(name,email,pass)
        }
    }

    private fun SignUp(name : String,email:String, pass:String){
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){ task->
            if (task.isSuccessful){
                addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                Toast.makeText(this@signUp_Page,"User Cearted Successfully",Toast.LENGTH_SHORT).show()
                val intent = Intent(this@signUp_Page,login_page::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@signUp_Page,"An Error Occurred",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name,email,uid))

    }
}
