package com.hungnd.dumachatfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

class MessageAdapter(val dataMessage: MutableList<MessageModel>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val showContentMessage = view.findViewById<TextView>(R.id.textView_message_text)
        val showTimeMessage = view.findViewById<TextView>(R.id.textView_message_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_show_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showContentMessage.text = dataMessage[position].content
        holder.showTimeMessage.text = dataMessage[position].messageTime
    }

    override fun getItemCount(): Int {
        return dataMessage.size
    }

}