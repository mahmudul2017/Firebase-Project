package com.gorentalbd.firebaseproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.gorentalbd.firebaseproject.model.User
import com.gorentalbd.firebaseproject.notification.NotificationActivity
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseDatabase.getReference("users").also { databaseReference = it }

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString()
            val gender = etGender.text.toString()

            if (!name.isNullOrBlank() && !age.isNullOrBlank() && !gender.isNullOrBlank()) {
                val user = User(name, age.toInt(), gender)
                Log.d("user", user.toString())

                databaseReference.child(name).setValue(user).addOnCompleteListener {
                    etName.text.clear()
                    etAge.text.clear()
                    etGender.text.clear()

                    Toast.makeText(this, "data successfully added !", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "please enter data !", Toast.LENGTH_LONG).show()
            }
        }

        btnView.setOnClickListener {
            startActivity(Intent(this, ViewActivity::class.java))
        }

        btnNotification.setOnClickListener {
            startActivity(Intent(this, NotificationActivity::class.java))
        }
    }
}