package com.example.team_val.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.team_val.R
import com.example.team_val.data.network.model.LineUpFBModel
import com.example.team_val.databinding.ItemAgentsBinding
import com.example.team_val.databinding.ItemLineupBinding
import com.example.team_val.doman.model.LineUpModel

class LineUpAdapter(var list: List<LineUpModel>):RecyclerView.Adapter<LineUpAdapter.LineUpViewHolder>() {


    class LineUpViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ItemLineupBinding.bind(view)
        fun bing(item : LineUpModel){
            binding.titleLineUp
            binding.rating.rating = (item.score) as Float
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineUpViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return LineUpViewHolder(layoutInflater.inflate(R.layout.item_lineup,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LineUpViewHolder, position: Int) {
    holder.bing(list[position])
        holder.itemView.setOnClickListener {

        }
    }
}