package com.example.hostelapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hostelapp.model.Notice

class NoticeAdapter(private val notices: List<Notice>) :
    RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder>() {

    class NoticeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvNoticeTitle)
        val message: TextView = itemView.findViewById(R.id.tvNoticeMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notice, parent, false)
        return NoticeViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticeViewHolder, position: Int) {
        val notice = notices[position]
        holder.title.text = notice.title
        holder.message.text = notice.message
    }

    override fun getItemCount() = notices.size
}