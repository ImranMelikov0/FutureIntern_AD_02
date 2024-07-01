package com.imranmelikov.futureintern_ad_02.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.imranmelikov.futureintern_ad_02.db.Note
import com.imranmelikov.futureintern_ad_02.db.NoteDatabase
import com.imranmelikov.futureintern_ad_02.db.User
import kotlinx.coroutines.launch

class NoteViewModel (application: Application): AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context=application.applicationContext
    private val noteDb= Room.databaseBuilder(context,NoteDatabase::class.java,"NoteDb").build()
    private val noteDao=noteDb.dao()

    private val noteMutableLiveData= MutableLiveData<List<Note>>()
    val noteLiveData: LiveData<List<Note>>
        get()=noteMutableLiveData

    private val userMutableLiveData= MutableLiveData<List<User>>()
    val userLiveData: LiveData<List<User>>
        get()=userMutableLiveData

    fun insertNote(note: Note){
        viewModelScope.launch {
            noteDao.insertNote(note)
        }
        getNoteList(note.parent)
    }
    fun updateNote(note: Note){
        viewModelScope.launch {
            noteDao.updateNote(note)
        }
        getNoteList(note.parent)
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            noteDao.deleteNote(note)
        }
        getNoteList(note.parent)
    }
    fun getNoteList(parent:String){
        viewModelScope.launch {
            noteMutableLiveData.value=noteDao.getNoteList(parent)
        }
    }
    fun insertUser(user: User){
        viewModelScope.launch {
            noteDao.insertUser(user)
        }
    }
    fun getUserList(){
        viewModelScope.launch {
            userMutableLiveData.value=noteDao.getUserList()
        }
    }
}