package com.hungnd.dumachatfirebase

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val dataMess = mutableListOf<MessageModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcv_ShowMess.setHasFixedSize(true)
        val adapter = MessageAdapter(dataMess)
        rcv_ShowMess.adapter = adapter
        adapter.notifyDataSetChanged()
        getMessage()
        imv_send.setOnClickListener {
            val userID = FirebaseAuth.getInstance().currentUser?.uid
            val contentMess = edt_content.text.toString()
            val c: Calendar = Calendar.getInstance()
            val df = SimpleDateFormat("MM-dd HH:mm")
            val formattedDate: String = df.format(c.time)
            if (userID != null) {
                writeNewMessage(userID, contentMess, formattedDate)
            }
            edt_content.text = null
        }
    }

    private fun writeNewMessage(userId: String, newMessage: String, messageTime: String) {
        val messageModel = MessageModel(userId, newMessage, messageTime)
        FirebaseDatabase.getInstance().reference.child("Message").push().setValue(messageModel)
            .addOnCompleteListener {
                Log.d("hungND", "Mess Successful")
            }.addOnFailureListener { Log.d("hungND", "Mess Fail") }
    }

    private var contentMessage: String? = null
    private var timeMessage: String? = null
    private fun getMessage() {
        val dataMessage =
            Firebase.database.reference.child("Message")
                .addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (snapshot in snapshot.children) {
                            val userID = FirebaseAuth.getInstance().currentUser?.uid
                            contentMessage = snapshot.child("content").value.toString()
                            timeMessage = snapshot.child("messageTime").value.toString()
                            val requsetMessage = MessageModel("", contentMessage, timeMessage)
                            dataMess.add(requsetMessage)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
    }
}