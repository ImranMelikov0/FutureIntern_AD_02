package com.imranmelikov.futureintern_ad_02.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.imranmelikov.futureintern_ad_02.MainActivity
import com.imranmelikov.futureintern_ad_02.R
import com.imranmelikov.futureintern_ad_02.RegisterActivity
import com.imranmelikov.futureintern_ad_02.adapter.NoteAdapter
import com.imranmelikov.futureintern_ad_02.alertdialog.LogOutAlertDialog
import com.imranmelikov.futureintern_ad_02.databinding.FragmentHomeBinding
import com.imranmelikov.futureintern_ad_02.viewmodel.NoteViewModel
import kotlin.math.log

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var viewModel: NoteViewModel
    private lateinit var userName: String
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        viewModel=ViewModelProvider(requireActivity())[NoteViewModel::class.java]

        sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        userName=sharedPreferences.getString("userName","")!!


        viewModel.getNoteList(userName)

        val bundle=Bundle()
        binding.fab.setOnClickListener {
            bundle.putInt("Insert",0)
            findNavController().navigate(R.id.action_homeFragment_to_uploadFragment,bundle)
        }
        binding.apply {
            registerForContextMenu(logoutBtn)
        }
        noteAdapter=NoteAdapter()
        initializeRv()
        observeNoteList()
        logOut()
        return binding.root
    }

    private fun observeNoteList(){
        viewModel.noteLiveData.observe(viewLifecycleOwner){noteList->
            if (noteList.isEmpty()){
                binding.emptyText.visibility=View.VISIBLE
            }else{
                binding.emptyText.visibility=View.GONE
            }
            val filteredNotes=noteList.filter { it.parent==userName }
            noteAdapter.noteList=filteredNotes
        }
    }
    private fun initializeRv(){
        binding.noteRv.adapter=noteAdapter
        binding.noteRv.layoutManager= GridLayoutManager(requireContext(),2)
    }

    private fun logOut(){
        binding.logoutBtn.setOnClickListener {
            val editor=sharedPreferences.edit()
            val logOutAlertDialog=LogOutAlertDialog()
            val intent= Intent(requireActivity(), RegisterActivity::class.java)
            logOutAlertDialog.alertDialog(requireContext(),R.layout.logout_alert_dialog,editor,intent,activity)
        }
    }
}