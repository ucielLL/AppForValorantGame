package com.example.team_val.ui.components

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.example.team_val.databinding.DialogAddLineupBinding
import java.util.Objects

class AddLineUpDialog(private val listNmae: List<String> ,private val onClik: (name: String)-> Unit):DialogFragment() {

    private lateinit var binding : DialogAddLineupBinding
    var name: String = ""

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogAddLineupBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.btnAddLineUp.isEnabled = false
        binding.btnAddLineUp.setOnClickListener {
            name =   binding.etName.editText?.text.toString()
            onClik.invoke(name)
        }
        binding.etName.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()) {
                    if(listNmae.isEmpty() )  {
                        binding.btnAddLineUp.isEnabled = true
                        binding.etName.error =null
                    }else
                        if (listNmae.contains(s.toString().lowercase())){
                            binding.btnAddLineUp.isEnabled = false
                            binding.etName.error ="el nombre ya esta en uso"
                        }
                        else{  binding.btnAddLineUp.isEnabled = true
                            binding.etName.error =null}
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })


        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

}