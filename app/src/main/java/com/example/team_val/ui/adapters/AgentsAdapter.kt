package com.example.team_val.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.team_val.R
import com.example.team_val.data.network.model.AgentsModel
import com.example.team_val.databinding.ItemAgentsBinding
import com.example.team_val.databinding.ItemListMapBinding
import dagger.hilt.android.AndroidEntryPoint


class AgentsAdapter(var list: List<AgentsModel>): RecyclerView.Adapter<AgentsAdapter.ViewHolderAgents>() {

    var onIteClik: ((position: Int)->Unit)? = null

    class ViewHolderAgents(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ItemAgentsBinding.bind(view)
        fun bing(item: AgentsModel){
            binding.tvRolAgent.text =item.role?.displayName
            binding.tvNameAgent.text = item.displayName
            Glide.with(itemView).load(item.displayIcon).into(binding.ItemimageAgent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAgents {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolderAgents(layoutInflater.inflate(R.layout.item_agents,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolderAgents, position: Int) {
       holder.bing(list[position])
        holder.itemView.setOnClickListener{
            onIteClik?.invoke(position)
        }

    }
}