package com.example.voting.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voting.R
import com.example.voting.data.entities.Voters
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<Voters>()


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int = userList.size
       // return userList.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
       //holder.itemView.tvId.text = currentItem.votersId.toString()
        holder.itemView.tvFirstName.text = currentItem.firstName
        holder.itemView.tvLastName.text = currentItem.lastName
        holder.itemView.tvAge.text = currentItem.votingCard
        
    }

    fun setData(user: List<Voters>){
        this.userList = user
        notifyDataSetChanged()
    }
}