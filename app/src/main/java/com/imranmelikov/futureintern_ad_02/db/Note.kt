package com.imranmelikov.futureintern_ad_02.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(val title:String,val description:String,val parent:String):Serializable {
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null
}