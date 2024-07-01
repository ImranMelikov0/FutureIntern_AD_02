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
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.imranmelikov.futureintern_ad_02.MainActivity
import com.imranmelikov.futureintern_ad_02.R
import com.imranmelikov.futureintern_ad_02.databinding.FragmentSignupBinding
import com.imranmelikov.futureintern_ad_02.db.User
import com.imranmelikov.futureintern_ad_02.viewmodel.NoteViewModel

class SignupFragment : Fragment() {
    private lateinit var binding:FragmentSignupBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: NoteViewModel
    private var userMutableList= mutableListOf<User>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignupBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(requireActivity())[NoteViewModel::class.java]
        sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        val checkRegister=sharedPreferences.getBoolean("register",false)
        if (checkRegister){
            val intent= Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }
        binding.alreadyAccountText.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }
        viewModel.getUserList()
        observeNoteList()

        signUp()
        return binding.root
    }

    private fun observeNoteList(){
        viewModel.userLiveData.observe(viewLifecycleOwner){listUser->
            listUser.map {
                userMutableList.add(it)
            }
        }
    }

    private fun signUp(){
        val name=binding.nameEdittext
        val password=binding.passwordEdittext
        binding.signupBtn.setOnClickListener {
            when{
                name.text.isEmpty()->{
                    Toast.makeText(requireContext(),"Enter name", Toast.LENGTH_SHORT).show()
                }
                password.text.isEmpty()->{
                    Toast.makeText(requireContext(), "Enter password", Toast.LENGTH_SHORT).show()
                }
                name.text.length>50->{
                    Toast.makeText(requireContext(), "You can enter a maximum of 50 characters!", Toast.LENGTH_SHORT).show()
                }
                password.text.length>50->{
                    Toast.makeText(requireContext(), "You can enter a maximum of 50 characters!", Toast.LENGTH_SHORT).show()
                }
                password.text.length<8->{
                    Toast.makeText(requireContext(), "You can enter a minimum of 8 characters!", Toast.LENGTH_SHORT).show()
                }
                name.text.isNotEmpty()&&password.text.isNotEmpty()->{
                   val user= userMutableList.find { it.name==name.text.toString() }
                    if (user?.name==name.text.toString()){
                        Toast.makeText(requireContext(),"This account already exist", Toast.LENGTH_SHORT).show()
                    } else{
                        val newUser=User(name.text.toString(),password.text.toString())
                        viewModel.insertUser(newUser)
                        val editor = sharedPreferences.edit()
                        editor.putBoolean("register",true)
                        editor.putString("userName",newUser.name)
                        editor.apply()
                        val intent= Intent(requireActivity(), MainActivity::class.java)
                        startActivity(intent)
                        activity?.finishAffinity()
                        userMutableList.clear()
                    }
                }
            }
        }
    }
}