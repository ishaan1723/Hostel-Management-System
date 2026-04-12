package com.example.hostelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelapp.model.ComplaintResponse

class ComplaintAdapter(private val complaints: List<ComplaintResponse>) :
    RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {

    class ComplaintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvComplaintTitle)
        val desc: TextView = itemView.findViewById(R.id.tvComplaintDesc)
        val status: TextView = itemView.findViewById(R.id.tvComplaintStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_complaint, parent, false)
        return ComplaintViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
        val c = complaints[position]
        holder.title.text = c.title
        holder.desc.text = c.description
        holder.status.text = "Status: ${c.status}"
    }

    override fun getItemCount() = complaints.size
}