package com.example.team_val.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.team_val.ResponseData
import com.example.team_val.databinding.ActivityRegisterBinding
import com.example.team_val.ui.view_model.RegisterViewModel
import com.example.team_val.data.network.firebase.AuthRes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.checkerframework.common.returnsreceiver.qual.This

@AndroidEntryPoint
class ActivityRegister : AppCompatActivity() {
    private val viewModel : RegisterViewModel by viewModels()
    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

      viewModel.authManagerInit()

      viewModel.buttonGoogleEnable.observe(this, Observer {
            binding.SignInGoole.isEnabled = it
        })
        viewModel.butoonRegiterEnable.observe(this, Observer {
            binding.BtnRegister.isEnabled = it

        })


        binding.usernmae.editText?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.userName = s.toString()
                viewModel.enableButtonGoogle()
            }
        })
        binding.userTag.editText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                viewModel.tagline = s.toString()
                viewModel.enableButtonGoogle()}
        })
        binding.userEmail.editText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               val  pattern = Patterns.EMAIL_ADDRESS;
                if ( !pattern.matcher(s).matches()){

                    binding.userEmail.error = "correo invalido"
                }
                else{
                    binding.userEmail.error = null
                    viewModel.email = s.toString()
                    viewModel.enableButtonRegiter()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        } )
        binding.userPasswordRepit.editText?.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ( binding.userPassword.editText?.text.toString() == s.toString() ){
                    binding.userPasswordRepit.error = null
                    viewModel.isPasswordcheked = true
                     viewModel.enableButtonRegiter()
                }
                else{
                    binding.userPasswordRepit.error = "error"
                    viewModel.isPasswordcheked = false

                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.BtnRegister.setOnClickListener {
            viewModel.createUserWithEmailAndPassword(binding.userPassword.editText?.text.toString())
        }
        binding.SignInGoole.setOnClickListener {
            viewModel.authManager.signInWithGoogle(this,googlLaucher)
        }
        binding.showLogInScreen.setOnClickListener {
            val intent= Intent(this,LogIn::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        viewModel.isCreateUser.observe(this, Observer{
            when(it){
                is  ResponseData.Success->{
                  updateUI()
                }
                is ResponseData.Error->{Toast.makeText(this,it.messageError,Toast.LENGTH_LONG ).show()}
            }
        })
    }

    var googlLaucher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult>() {
          viewModel.createUserWithGoogle(it)
        }
    )
    fun updateUI(){
        val intent= Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}