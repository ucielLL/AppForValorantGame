package com.example.team_val.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.set
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.room.InvalidationTracker
import com.example.team_val.R
import com.example.team_val.ResponseData
import com.example.team_val.databinding.FragmentInfoUserBinding
import com.example.team_val.databinding.FragmentMainBinding
import com.example.team_val.ui.view_model.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentInfoUser : Fragment() {

    private  val  viewModel  by viewModels<UserInfoViewModel>()
    private var _binding: FragmentInfoUserBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoUserBinding.inflate(inflater,container,false)
        initdata()

        viewModel.responseMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity,it,Toast.LENGTH_LONG).show()
        })
        viewModel.responsDelateUser.observe(viewLifecycleOwner, Observer {
            when( it){
                is ResponseData.Success->{
                    val intent = Intent(activity,LogIn::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                is ResponseData.Error->{
                    Toast.makeText(activity,it.messageError,Toast.LENGTH_LONG).show()
                }
            }
        })
        binding.BtnUpdateDataUser.setOnClickListener {
            viewModel.updateInfo(binding.UserNmaeValorant.text.toString(),binding.Usertag.text.toString())
        }
        binding.btnDeleteAcount.setOnClickListener {
         viewModel.delateUser()}

        binding.btnSignOut.setOnClickListener{
            viewModel.singOut()
            val intent = Intent(activity,LogIn::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }
        return binding.root
    }
    fun initdata(){
        var data=  viewModel.loadData()
        binding.EmailUser.text = data.first
        binding.UserNmaeValorant.hint =data.second
    }
}