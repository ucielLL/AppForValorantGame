package com.example.team_val.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team_val.R
import com.example.team_val.databinding.DialogAddLineupBinding
import com.example.team_val.databinding.FragmentLineupListBinding
import com.example.team_val.databinding.FragmentMapsBinding
import com.example.team_val.doman.model.LineUpModel
import com.example.team_val.ui.adapters.LineUpAdapter
import com.example.team_val.ui.components.AddLineUpDialog
import com.example.team_val.ui.view_model.MainVieModel
import com.example.team_val.ui.view_model.MapsLineUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LineupList : Fragment() {

    val arg: LineupListArgs by navArgs()
    private var _binding: FragmentLineupListBinding ? = null
    private val binding get() = _binding!!
    private var listName: List<String> = emptyList()
    private lateinit var viewModel: MapsLineUpViewModel
    private  lateinit var adapter: LineUpAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get( MapsLineUpViewModel::class.java)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLineupListBinding.inflate(inflater,container,false)
        binding.TitleLineupList.text = "alineación: " +arg.nameMap
        binding.floatingActionButton.setOnClickListener{
            AddLineUpDialog(listName){
               findNavController().navigate(LineupListDirections.actionLineupListToCrearLineUp(name = it))
            }.show(childFragmentManager,"dialogAddLU")
        }
        viewModel.listLineUp.observe(viewLifecycleOwner, Observer {
            listName = it.map { it.name.lowercase() }.toList()
            initRecyclerview(it)
        })
        viewModel.responseMessage.observe(viewLifecycleOwner,Observer{
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
        })
        viewModel.getLineUps(arg.nameMap)
        return binding.root
    }
    fun initRecyclerview(list : List<LineUpModel>){
        adapter = LineUpAdapter(list)
        binding.maps.layoutManager = LinearLayoutManager(activity)
        binding.maps.adapter =adapter
    }
}