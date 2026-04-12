package com.example.hostelapp
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelapp.model.Room

class RoomAdapter(private val roomList: List<Room>) :
    RecyclerView.Adapter<RoomAdapter.RoomViewHolder>() {

    class RoomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val roomNumber: TextView = itemView.findViewById(R.id.tvRoomNumber)
        val capacity: TextView = itemView.findViewById(R.id.tvCapacity)
        val available: TextView = itemView.findViewById(R.id.tvAvailable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room, parent, false)
        return RoomViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val room = roomList[position]
        holder.roomNumber.text = "Room: ${room.roomNumber}"
        holder.capacity.text = "Capacity: ${room.capacity}"
        holder.available.text = "Available: ${room.capacity - room.occupiedBeds}" // ✅ fixed
    }
    override fun getItemCount(): Int = roomList.size
}