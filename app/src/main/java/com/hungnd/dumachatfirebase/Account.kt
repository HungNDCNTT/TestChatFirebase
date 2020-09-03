package com.hungnd.dumachatfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_account.*

class Account : AppCompatActivity() {
    private lateinit var databaseRf: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        btn_Create.setOnClickListener {
            val email = edt_Email.text.toString()
            val password = edt_Password.text.toString()

            addAuthentication(email, password)
        }
        btn_Login.setOnClickListener {
            getAccountUser(edt_Email.text.toString(), edt_Password.text.toString())

        }
    }

    private fun getAccountUser(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val userID = FirebaseAuth.getInstance().currentUser?.uid
                val username = edt_Username.text.toString()
                val status = true
                if (userID != null) {
                    writeNewAccount(userID, username, status)
                }
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addAuthentication(email: String, username: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, username)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("hungND", "Successful ${it.result?.user?.uid}")
                } else {
                    Log.e("hungND", it.exception.toString())
                }
            }.addOnFailureListener {
                Log.e("hungND", "Fail")
            }
    }

    private fun writeNewAccount(userId: String, newUserName: String, newStatus: Boolean) {
        val userModel = UserModel(userId, newUserName, newStatus)
        FirebaseDatabase.getInstance().reference.child("Account").push().setValue(userModel)
            .addOnCompleteListener {
                Log.d("hungND", "Successful")
            }.addOnFailureListener { Log.d("hungND", "Fail") }
    }


}