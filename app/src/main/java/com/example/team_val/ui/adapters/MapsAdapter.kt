package com.example.team_val.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.team_val.R
import com.example.team_val.databinding.ItemListMapBinding
import com.example.team_val.doman.model.MapModel

class MapsAdapter(val maps: List<MapModel>):
    RecyclerView.Adapter<MapsAdapter.MapViewHolder>() {

    var onclik: ((mapName:String)->Unit)? =null

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapViewHolder {
         val layoutInflater = LayoutInflater.from(parent.context)
         return MapViewHolder(layoutInflater.inflate(R.layout.item_list_map,parent,false))
     }

     override fun onBindViewHolder(holder: MapViewHolder, position: Int) {
      val item = maps[position]
       holder.bing(item)
         holder.itemView.setOnClickListener{onclik?.invoke(maps[position].name)}
     }

     override fun getItemCount(): Int {
        return maps.size
     }

    class MapViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val binding = ItemListMapBinding.bind(view)

        @SuppressLint("CheckResult")
        fun bing(mapModel: MapModel){
            binding.nameMap.text = mapModel.name
            Glide.with(itemView).load(mapModel.path).into(binding.imgMap)
        }
    }
}
@GlideModule
class CustomGlideModule : AppGlideModule()