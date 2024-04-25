package com.example.team_val.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.team_val.ResponseData
import com.example.team_val.databinding.ActivityLogInBinding
import com.example.team_val.ui.view_model.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogIn : AppCompatActivity() {

    private val viewModel :LogInViewModel by viewModels()
    private lateinit var binding: ActivityLogInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.BtnLogIn.isEnabled = false
        binding.BtnLogIn.setOnClickListener {
            if(!binding.userPassword.editText?.text.toString().isNullOrEmpty()){
                viewModel.signInWithEmail(binding.userPassword.editText?.text.toString())
            }
            else{
                binding.userPassword.isErrorEnabled = true
            }
        }
        binding.SignInGoole.setOnClickListener {
            viewModel.authManager.signInWithGoogle(this,googlLaucher)
        }
        binding.userPassword.editText?.addTextChangedListener (object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s.toString().length > 1){   binding.userPassword.isErrorEnabled = false}

            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.useremail.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val  pattern = Patterns.EMAIL_ADDRESS;
                if ( !pattern.matcher(s).matches()){
                    viewModel.email
                    binding.BtnLogIn.isEnabled = true
                }
                else{ binding.BtnLogIn.isEnabled = false}
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.showRegisterScreen.setOnClickListener {

            val intent= Intent(this,ActivityRegister::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }

        viewModel.isSigned.observe(this, Observer {
            when(it){
            is  ResponseData.Success->{
            updateUI()
        }
            is ResponseData.Error->{Toast.makeText(this,it.messageError,Toast.LENGTH_LONG ).show()}
        }
        })

        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })

    }
    override fun onStart() {
        super.onStart()
       val user = viewModel.authManager.getCurrentUser()
        if(user != null) {updateUI()}
    }
    var googlLaucher: ActivityResultLauncher<Intent> = registerForActivityResult(
         ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult>() {
             viewModel.signInGoogle(it)
        }
    )

    fun updateUI(){
        val intent= Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}