package com.imranmelikov.futureintern_ad_02.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.imranmelikov.futureintern_ad_02.R
import com.imranmelikov.futureintern_ad_02.alertdialog.AlertDialogDelete
import com.imranmelikov.futureintern_ad_02.databinding.FragmentUploadBinding
import com.imranmelikov.futureintern_ad_02.db.Note
import com.imranmelikov.futureintern_ad_02.viewmodel.NoteViewModel

@Suppress("DEPRECATION")
class UploadFragment : Fragment() {
  private lateinit var binding:FragmentUploadBinding
    private lateinit var viewModel: NoteViewModel
    private var note: Note?=null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userName:String
    private lateinit var alertDialogDelete:AlertDialogDelete
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentUploadBinding.inflate(inflater,container,false)
        viewModel= ViewModelProvider(requireActivity())[NoteViewModel::class.java]

        sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        userName=sharedPreferences.getString("userName","")!!

        alertDialogDelete= AlertDialogDelete()
        alertDialogDelete.viewModel=viewModel

        val checkFragment=arguments?.getInt("Insert")
        if (checkFragment==0){
            binding.apply {
                deleteBtn.visibility=View.GONE
            }
        }else{
            note=arguments?.getSerializable("note") as Note
            binding.titleEditText.setText(note!!.title)
            binding.editText.setText(note!!.description)
            deleteTask()
        }
        clickBtn()
        return binding.root
    }

    private fun deleteTask(){
        binding.deleteBtn.setOnClickListener {
            alertDialogDelete.alertDialog(requireContext(),R.layout.delete_alert_dialog,note!!,it)
        }
    }

    private fun clickBtn(){
        binding.apply {
            backBtn.setOnClickListener {
                editText.text?.let {
                    val title = titleEditText.text.trim()
                    val description = it.trim()
                    if (title.isEmpty() && description.isEmpty()) {
                        findNavController().popBackStack()
                    } else if (title.length > 50) {
                        Toast.makeText(requireContext(), "You can enter a maximum of 50 characters!", Toast.LENGTH_SHORT).show()
                    }  else if (description.length > 500) {
                        Toast.makeText(requireContext(), "You can enter a maximum of 500 characters!", Toast.LENGTH_SHORT).show()
                    }else{
                        var titleString=""
                        titleString = if (title.isEmpty()){
                            "Title"
                        }else{
                            title.toString()
                        }
                        val newNote= Note(titleString,description.toString(),userName)
                        if (note!=null){
                            newNote.id=note?.id
                            viewModel.updateNote(newNote)
                        }else{
                            viewModel.insertNote(newNote)
                        }
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}