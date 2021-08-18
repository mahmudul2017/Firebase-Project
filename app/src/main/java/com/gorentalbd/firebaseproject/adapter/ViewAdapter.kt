package com.gorentalbd.firebaseproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gorentalbd.firebaseproject.R
import com.gorentalbd.firebaseproject.model.User
import kotlinx.android.synthetic.main.view_sample.view.*

class ViewAdapter (private var userList: ArrayList<User>, private val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<ViewAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_sample, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var user = userList[position]
        holder.itemView.tvName.text = "User Name: ${user.name}"
        holder.itemView.tvAge.text = "User Age: ${user.age.toString()}"
        holder.itemView.tvGender.text = "User Gender: ${user.gender}"

        holder.itemView.setOnClickListener {
            //onItemClickListener.onItemClick(user)
        }

        holder.itemView.btnDelete.setOnClickListener {
            onItemClickListener.onItemClick(user)
        }
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(user: User?)
    }
}