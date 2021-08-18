package com.gorentalbd.firebaseproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.gorentalbd.firebaseproject.adapter.ViewAdapter
import com.gorentalbd.firebaseproject.model.User
import kotlinx.android.synthetic.main.activity_view.*

class ViewActivity : AppCompatActivity() {
    private lateinit var viewAdapter: ViewAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        databaseReference = FirebaseDatabase.getInstance().getReference("users")
        userList = arrayListOf<User>()

        /*viewAdapter = ViewAdapter(userList, object : ViewAdapter.OnItemClickListener {
            override fun onItemClick(user: User?) {
                Toast.makeText(this@ViewActivity, "click ${user!!.name}", Toast.LENGTH_LONG).show()
            }
        })*/
        recyclerView.layoutManager = LinearLayoutManager(this)
        //recyclerView.adapter = viewAdapter
        Log.d("user", userList.toString())

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userList.clear()
                    for (dataList in snapshot.children) {
                        val user = dataList.getValue(User::class.java)
                        userList.also {
                            it.add(user!!)
                            Log.d("data", "add: $user")
                        }
                    }
                    viewAdapter = ViewAdapter(userList, object : ViewAdapter.OnItemClickListener {
                        override fun onItemClick(user: User?) {
                            databaseReference.child(user!!.name.toString()).removeValue().addOnCompleteListener {
                                Toast.makeText(this@ViewActivity, "delete ${user!!.name}", Toast.LENGTH_LONG).show()
                                //userList.clear()
                                viewAdapter.notifyDataSetChanged()
                            }.addOnFailureListener {
                                Toast.makeText(this@ViewActivity, "delete failed", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                    recyclerView.adapter = viewAdapter
                    Log.d("user", userList.toString())
                }
                //viewAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("data", "error: $error")
            }
        })
    }
}