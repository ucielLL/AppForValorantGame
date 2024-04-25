package com.example.team_val.ui.fragments_botton_menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team_val.R
import com.example.team_val.data.network.model.AgentsModel
import com.example.team_val.databinding.FragmentAgentsBinding
import com.example.team_val.doman.model.MapModel
import com.example.team_val.ui.adapters.AgentsAdapter
import com.example.team_val.ui.adapters.MapsAdapter
import com.example.team_val.ui.view_model.AgentsViewModel
import com.example.team_val.ui.view_model.MainVieModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AgentsFragment : Fragment() {
    private var _binding: FragmentAgentsBinding ? = null
    private val binding get()  = _binding!!
    private lateinit var viewModel: AgentsViewModel
  // private lateinit var viewModel: MainVieModel
    private lateinit var adapter: AgentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AgentsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgentsBinding.inflate(inflater,container,false)
        viewModel.getAgent()
        viewModel.listagents.observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })
        viewModel.responseMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }
    private fun initRecyclerView(list: List<AgentsModel>) {
        adapter = AgentsAdapter(list)
        _binding!!.agents.layoutManager = LinearLayoutManager(activity)
        _binding!!.agents.adapter = adapter

        adapter.onIteClik = {
            viewModel.positionDetile = it
            findNavController().navigate(R.id.action_agentsFragment_to_fragment_agent)
        }

    }
}