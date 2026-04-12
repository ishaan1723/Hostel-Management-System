package com.example.hostelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelapp.model.User

class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvName)
        val email: TextView = itemView.findViewById(R.id.tvEmail)
        val room: TextView = itemView.findViewById(R.id.tvRoom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.name.text = user.fullName.ifEmpty { "No Name" }
        holder.email.text = user.username
        holder.room.text = if (user.roomNumber != null) "Room: ${user.roomNumber}" else "Not Assigned"  // ✅
    }
    override fun getItemCount(): Int = userList.size
}