package com.imranmelikov.futureintern_ad_02.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.imranmelikov.futureintern_ad_02.MainActivity
import com.imranmelikov.futureintern_ad_02.R
import com.imranmelikov.futureintern_ad_02.RegisterActivity
import com.imranmelikov.futureintern_ad_02.databinding.FragmentLoginBinding
import com.imranmelikov.futureintern_ad_02.db.User
import com.imranmelikov.futureintern_ad_02.viewmodel.NoteViewModel

class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var userMutableList= mutableListOf<User>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLoginBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(requireActivity())[NoteViewModel::class.java]
        sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        viewModel.getUserList()
        observeNoteList()

        logIn()
        binding.alreadyAccountText.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

    private fun observeNoteList(){
        viewModel.userLiveData.observe(viewLifecycleOwner){userList->
            userList.map {
                userMutableList.add(it)
            }
        }
    }
    private fun logIn(){
        val name=binding.nameEdittext
        val password=binding.passwordEdittext
        binding.loginBtn.setOnClickListener {
            when{
                name.text.isNotEmpty()&&password.text.isNotEmpty()->{
                    val user= userMutableList.find { it.name==name.text.toString()&&it.password==password.text.toString() }
                    if (user?.name==name.text.toString()&&user.password==password.text.toString()){
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("register",true)
                        editor.putString("userName",name.text.toString())
                        editor.apply()
                        val intent=Intent(requireActivity(),MainActivity::class.java)
                        startActivity(intent)
                        activity?.finishAffinity()
                        userMutableList.clear()
                    }else{
                        Toast.makeText(requireContext(),"Name or Password is wrong!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}