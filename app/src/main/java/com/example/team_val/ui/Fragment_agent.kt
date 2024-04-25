package com.example.team_val.ui

import android.app.Activity
import android.os.Bundle
import android.text.method.MovementMethod
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.team_val.R
import com.example.team_val.ResponseData
import com.example.team_val.databinding.FragmentAgentBinding
import com.example.team_val.ui.view_model.AgentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Fragment_agent : Fragment() {
    private var _binding: FragmentAgentBinding ? = null
    private val binding get() = _binding!!
   private lateinit var viewModel: AgentsViewModel
   // private lateinit var viewModel: MainVieModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AgentsViewModel::class.java)
        //viewModel = ViewModelProvider(requireActivity())[MainVieModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgentBinding.inflate(inflater,container,false)
        initAgent()


        return binding.root
    }
    private fun initAgent(){
        when(val agent = viewModel.getAgentDetail()){
            is ResponseData.Success->{
               Glide.with(this).load(agent.data.fullPortrait).into( binding.imgAgent)
                Glide.with(this).load(agent.data.role?.displayIcon).into( binding.imgRol)
                binding.tvName.text = agent.data.displayName
                binding.tvDescrition.text= agent.data.description

                agent.data.abilities.forEach {ability->
                    val inflater: LayoutInflater = LayoutInflater.from(context)
                    val view: View = inflater.inflate(R.layout.item_abilities,binding.listAbilities,false)
                    val img : ImageView = view.findViewById(R.id.imgRol)
                    val slote: TextView = view.findViewById(R.id.tvslot)
                    val slotename: TextView = view.findViewById(R.id.tvnamrSlot)
                    val description: TextView = view.findViewById(R.id.tvDescritionAbiliti)
                    Glide.with(view).load(ability.displayIcon).into(img)
                    slote.text = ability.slot
                    slotename.text = ability.displayName
                    description.text = ability.description
                    description.movementMethod =  ScrollingMovementMethod()

                    binding.listAbilities.addView(view)
                }
            }
            is ResponseData.Error->{
                Toast.makeText(activity,agent.messageError,Toast.LENGTH_SHORT).show()
            }

        }
    }

}