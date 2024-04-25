package com.example.team_val.ui.fragments_botton_menu

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.team_val.R
import com.example.team_val.databinding.FragmentMainBinding
import com.example.team_val.ui.adapters.ItemTeamAdapter
import com.example.team_val.ui.view_model.MainVieModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainVieModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainVieModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        //bindig.UsersList.adapter = ItemTeamAdapter(this)
        binding.nameUser.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_fragmentInfoUser)
        }
        viewModel.responseMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
        })
        return binding.root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}