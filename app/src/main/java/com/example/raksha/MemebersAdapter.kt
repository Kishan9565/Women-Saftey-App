package com.example.raksha

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.raksha.data.membersData

class MemebersAdapter(private var members : List<membersData>, context: Context):RecyclerView.Adapter<MemebersAdapter.MemberViewHolder>(){

    class MemberViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val names: TextView = itemView.findViewById(R.id.tvName)
        val number : TextView = itemView.findViewById(R.id.tvnumber)
        val msg : TextView = itemView.findViewById(R.id.tvmsg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.members_item, parent, false)
        return MemberViewHolder(view)
    }

    override fun getItemCount():Int =  members.size

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val member = members[position]
        holder.names.text = member.name
        holder.number.text = member.phoneNumber
        holder.msg.text = member.msg
    }

    fun refreshData(newMembers : List<membersData>){
        members = newMembers
        notifyDataSetChanged()
    }
}