package com.imranmelikov.futureintern_ad_02.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([Note::class,User::class], version = 1)
abstract class NoteDatabase:RoomDatabase() {
    abstract fun dao():NoteDao
}