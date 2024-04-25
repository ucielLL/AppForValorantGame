package com.example.team_val.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.team_val.R
import com.example.team_val.databinding.ItemteamBinding
import com.google.android.material.imageview.ShapeableImageView

class ItemTeamAdapter(private val context: Context, private val arrayList: ArrayList<String>):ArrayAdapter<String>(context,
         R.layout.itemteam,arrayList){

    @SuppressLint("MissingInflatedId")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater= LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.itemteam,parent,false)
        val image: ShapeableImageView = view.findViewById(R.id.ItemimageUser)
        val userNmae : TextView = view.findViewById(R.id.Text_ItemUserName)
        val state : ImageView = view.findViewById(R.id.image_ItemUserState)
         //userNmae.text = arrayList[position]

        return view
    }
}