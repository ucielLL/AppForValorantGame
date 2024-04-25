package com.example.team_val.ui.fragments_botton_menu

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team_val.R
import com.example.team_val.data.network.firebase.FirestoreManager
import com.example.team_val.databinding.FragmentMainBinding
import com.example.team_val.databinding.FragmentMapsBinding
import com.example.team_val.doman.model.MapModel
import com.example.team_val.ui.adapters.MapsAdapter
import com.example.team_val.ui.view_model.MainVieModel
import com.example.team_val.ui.view_model.MapsLineUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding ? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MapsLineUpViewModel
    private  lateinit var adapter: MapsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MapsLineUpViewModel::class.java)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMapsBinding.inflate(inflater,container,false)
        loadMaps()
        viewModel.
            listMap.observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })


        return binding.root
    }
    fun initRecyclerView(list: List<MapModel>) {
        adapter = MapsAdapter(list)
        adapter.onclik ={

            findNavController().navigate(MapsFragmentDirections.actionMapsFragmentToLineupList(nameMap = it))
        }
        _binding!!.maps.layoutManager = GridLayoutManager(activity,2)
        _binding!!.maps.adapter = adapter


    }
    fun loadMaps(){
        viewModel.loadMaps()
        Log.w(TAG, "ejecutar load data 1")

        if(viewModel.listMap.value?.isEmpty() == true){
            Log.w(TAG, " lisa masp vacia ")
            activity?.let { viewModel.downloadata(it.baseContext) }
            Log.w(TAG, "ejecua dowload data")
            viewModel.loadMaps()
            Log.w(TAG, "lista de maps"+ viewModel.listMap.value?.toString())
        }
    }
}