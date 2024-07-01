package com.imranmelikov.futureintern_ad_02.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.imranmelikov.futureintern_ad_02.R
import com.imranmelikov.futureintern_ad_02.databinding.NotesRvBinding
import com.imranmelikov.futureintern_ad_02.db.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val binding:NotesRvBinding): RecyclerView.ViewHolder(binding.root)

    // DiffUtil for efficient RecyclerView updates
    private val diffUtil=object : DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem==newItem
        }
    }
    private val recyclerDiffer= AsyncListDiffer(this,diffUtil)

    // Getter and setter list note
    var noteList:List<Note>
        get() = recyclerDiffer.currentList
        set(value) = recyclerDiffer.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding=NotesRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note=noteList[position]
        holder.binding.apply {
            pageMainTitle.text=note.title
        }

        holder.itemView.setOnClickListener {
            val bundle= Bundle()
            bundle.putInt("Insert",1)
            bundle.putSerializable("note",note)
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_uploadFragment,bundle)
        }
    }
}